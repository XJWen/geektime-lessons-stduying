package org.geektime.context;

import org.geektime.function.ThrowableAction;
import org.geektime.function.ThrowableFunction;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.naming.*;
import javax.servlet.ServletContext;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Java 传统组件上下文（基于 JNDI实现）
 */
public class ClassicComponentContext implements ComponentContext{

    public static final String CONTEXT_NAME = ClassicComponentContext.class.getName();

    private static final String COMPONENT_ENV_CONTEXT_NAME = "java:comp/env";

    /**
     * // 假设一个 Tomcat JVM 进程，三个 Web Apps，会不会相互冲突？（不会冲突）
     *  // static 字段是 JVM 缓存吗？（是 ClassLoader 缓存）
     * */
    private static final Logger logger = Logger.getLogger(CONTEXT_NAME);

    private static ServletContext servletContext;

    //    private static ApplicationContext applicationContext;

//    public void setApplicationContext(ApplicationContext applicationContext){
//        ComponentContext.applicationContext = applicationContext;
//        WebApplicationContextUtils.getRootWebApplicationContext()
//    }

    private Context envContext;

    private ClassLoader classLoader;

    private Map<String, Object> componentsCache = new LinkedHashMap<String, Object>();

    /**
     * @PreDestroy 方法缓存，key为标注方法，Value为方法所属对象
     * */
    private Map<Method, Object> preDestroyMethodCache = new LinkedHashMap<Method, Object>();

    /**
     * 获取 ComponentContext
     * @return
     */
    public static ClassicComponentContext getInstance(){
        return (ClassicComponentContext) servletContext.getAttribute(CONTEXT_NAME);
    }

    public void init(ServletContext servletContext)throws RuntimeException{
        ClassicComponentContext.servletContext = servletContext;
        servletContext.setAttribute(CONTEXT_NAME,this);
        this.init();
    }

    /**
     * 初始化上下文
     */
    @Override
    public void init() {
        initClassLoader();
        initEnvContext();
        instantiateComponents();
        initializeComponents();
        registerShutdownHook();
    }

    private void initClassLoader() {
        // 获取当前 ServletContext（WebApp）ClassLoader
        this.classLoader = servletContext.getClassLoader();
    }

    private void initEnvContext() {
        if (this.envContext!=null){
            return;
        }
        Context context = null;
        try {
            context = new InitialContext();
            this.envContext = (Context) context.lookup(COMPONENT_ENV_CONTEXT_NAME);
        }catch (NamingException ex){
            throw new  RuntimeException(ex);
        }finally {
            close(context);
        }
    }

    private static void close(Context context){
        if (context != null){
            ThrowableAction.execute(context::close);
        }
    }

    /**
     * 实例化组件
     */
    private void instantiateComponents() {
        // 遍历获取所有的组件名称
        List<String> componentNames = listAllComponentNames();
        // 通过依赖查找，实例化对象（ Tomcat BeanFactory setter 方法的执行，仅支持简单类型）
        componentNames.forEach(name->componentsCache.put(name,lookupComponent(name)));
    }


    protected List<String> listAllComponentNames() {
        return listComponentNames("/");
    }

    protected List<String> listComponentNames(String name) {
        return executeInContext(context->{
            NamingEnumeration<NameClassPair> ne = executeInContext(context,ctx->ctx.list(name),true);

            // 目录 - Context
            // 节点 -
            if (ne == null) { // 当前 JNDI 名称下没有子节点
                return Collections.emptyList();
            }
            List<String> fullNames = new LinkedList<>();
            while(ne.hasMoreElements()){
                NameClassPair element = ne.nextElement();
                String className = element.getClassName();
                Class<?> targetClass = classLoader.loadClass(className);
                if (Context.class.isAssignableFrom(targetClass)){
                    // 如果当前名称是目录（Context 实现类）的话，递归查找
                    fullNames.addAll(listComponentNames(element.getName()));
                }else{
                    // 否则，当前名称绑定目标类型的话话，添加该名称到集合中
                    String fullName = name.startsWith("/")?element.getName():name+"/"+element.getName();
                    fullNames.add(fullName);
              }
            }
            return fullNames;
        });
    }
    /**
     * 在 Context 中执行，通过指定 ThrowableFunction 返回计算结果
     *
     * @param function ThrowableFunction
     * @param <R>      返回结果类型
     * @return 返回
     * @see ThrowableFunction#apply(Object)
     */
    protected <R>R executeInContext(ThrowableFunction<Context,R> function) {
        return  executeInContext(function,false);
    }

    protected <R> R executeInContext(ThrowableFunction<Context, R> function, boolean ignoredException) {
        return executeInContext(this.envContext,function,ignoredException);
    }

    private <R> R executeInContext(Context context, ThrowableFunction<Context, R> function,
                                   boolean ignoredException) {
        R result = null;

        try {
            result = ThrowableFunction.execute(context,function);
        }catch (Throwable ex){
            if (ignoredException){
                logger.warning(ex.getMessage());
            }else{
                throw new RuntimeException(ex);
            }
        }
        return  result;
    }

    private <C>C lookupComponent(String name) {
        return executeInContext(context -> (C)context.lookup(name));
    }

    /**
     * 初始化组件（支持 Java 标准 Commons Annotation 生命周期）
     * <ol>
     *  <li>注入阶段 - {@link Resource}</li>
     *  <li>初始阶段 - {@link PostConstruct}</li>
     *  <li>销毁阶段 - {@link PreDestroy}</li>
     * </ol>
     */
    private void initializeComponents() {
        componentsCache.values().forEach(this::initializeComponent);
    }

    /**
     * 初始化组件（支持 Java 标准 Commons Annotation 生命周期）
     * <ol>
     *  <li>注入阶段 - {@link Resource}</li>
     *  <li>初始阶段 - {@link PostConstruct}</li>
     *  <li>销毁阶段 - {@link PreDestroy}</li>
     * </ol>
     */
    private void initializeComponent(Object component) {
        Class<?> componentClass =  component.getClass();
        // 注入阶段 - {@link Resource}
        injectComponent(component,componentClass);
        // 查询候选方法
        List<Method> candidateMethods = findCandidateMethods(componentClass);
        // 初始化阶段 - {@link PostConstruct}
        processPostConstruct(component,candidateMethods);
        //本阶段处理 {@link PreDestroy} 方法元数据
        processPreDestroyMetadata(component,candidateMethods);
    }


    private void injectComponent(Object component, Class<?> componentClass) {
        Stream.of(componentClass.getDeclaredFields())
                .filter(field -> {
                    int mods = field.getModifiers();
                    //静态&&注解@Resource
                    return !Modifier.isStatic(mods)&&field.isAnnotationPresent(Resource.class);
                }).forEach(field -> {
                    Resource resource = field.getAnnotation(Resource.class);
                    String resourceName = resource.name();
                    Object injectedObject = lookupComponent(resourceName);
                    field.setAccessible(true);

                    try{
                        //注入目标对象
                        field.set(component,injectedObject);
                    }catch (IllegalAccessException ex){
                        throw new  RuntimeException(ex);
                    }
        });
    }

    /**
     * 获取组件类中的候选方法
     *
     * @param componentClass 组件类
     * @return non-null
     */
    private List<Method> findCandidateMethods(Class<?> componentClass) {
        // public 方法
        return Stream.of(componentClass.getMethods())
                .filter(method ->
                   !Modifier.isStatic(method.getModifiers())   //非static
                           &&(method.getParameterCount()==0)   //无参方法
                )
                .collect(Collectors.toList());
    }

    private void processPostConstruct(Object component, List<Method> candidateMethods) {
        candidateMethods
                .stream()
                .filter(method -> method.isAnnotationPresent(PostConstruct.class))   //标注为@PostConstruct
                .forEach(method -> {
                   //执行目标方法
                   ThrowableAction.execute(() ->method.invoke(component));
                });

    }

    /**
     * @param component        组件对象
     * @param candidateMethods 候选方法
     * @see #processPreDestroy()
     */
    private void processPreDestroyMetadata(Object component, List<Method> candidateMethods) {
        candidateMethods
                .stream()
                .filter(method -> method.isAnnotationPresent(PreDestroy.class)) //标注为@PreDestroy
                .forEach(method -> {
                    preDestroyMethodCache.put(method,component);
                });
    }


    private void registerShutdownHook() {
        //另起线程操作
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            processPreDestroy();
        }));

    }


    /**
     * 上下文销毁方法
     */
    @Override
    public void destroy() {
        processPreDestroy();
        clearCache();
        closeEnvContext();
    }

    private void processPreDestroy() {
    }

    private void clearCache() {
    }

    private void closeEnvContext() {
    }


    /**
     * 通过名称查找组件对象
     *
     * @param name 组件名称
     * @return 如果找不到返回, <code>null</code>
     */
    @Override
    public <C> C getComponent(String name) {
        return (C)componentsCache.get(name);
    }

    /**
     * 获取所有的组件名称
     *
     * @return
     */
    @Override
    public List<String> getComponentNames() {
        return new ArrayList<>(componentsCache.keySet());
    }
}
