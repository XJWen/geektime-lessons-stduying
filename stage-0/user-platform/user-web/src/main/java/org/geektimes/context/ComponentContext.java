package org.geektimes.context;

import org.geektimes.function.ThrowableAction;
import org.geektimes.function.ThrowableFunction;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.naming.*;
import javax.servlet.ServletContext;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * 组件上下文（Web 应用全局使用）
 */
public class ComponentContext {
    //获取类名(全局名)
    public static final String CONTEXT_NAME = ComponentContext.class.getName();

    //定义JNDI context
    private static final String COMPONENT_ENV_CONTEXT_NAME = "java:comp/env";

    private static final Logger logger = Logger.getLogger(CONTEXT_NAME);
    // Component Env Context
    public Context context;
    //尽量不要暴露ServletContext
    private static ServletContext servletContext ;
    // 假设一个 Tomcat JVM 进程，三个 Web Apps，会不会相互冲突？（不会冲突）
    // static 字段是 JVM 缓存吗？（是 ClassLoader 缓存）

    private ClassLoader clazzLoader;
    //装载组件名和组件实例化对象的Map存储
    private Map<String, Object> componentsMap = new LinkedHashMap<>();


    public void init(ServletContext servletContext) throws RuntimeException{
/*        try{
            this.context = (Context)new InitialContext().lookup(COMPONENT_ENV_CONTEXT_NAME);
        }catch (NamingException ex){
            throw new RuntimeException(ex);
        }*/
        //注入类
        ComponentContext.servletContext=servletContext;
        servletContext.setAttribute(CONTEXT_NAME,this);
        // 获取当前 ServletContext（WebApp）ClassLoader
        this.clazzLoader=servletContext.getClassLoader();
        initEnvContext();
        instantiateComponents();
        initializeComponents();
    }

    /**
     * 初始化Context
     * **/
    private void initEnvContext() throws RuntimeException{
        if (this.context!=null){
            return;
        }
        Context context = null;
        try {
            context = new InitialContext();
            this.context = (Context) context.lookup(COMPONENT_ENV_CONTEXT_NAME);
        }catch (NamingException ex){
            throw new RuntimeException(ex);
        }finally {
            close(context);
        }
    }
    /**
     * 实例化组件
     */
    protected void instantiateComponents(){
        // 遍历context.xml获取所有的组件名称
        List<String> componentNames = listAllComponentNames();
        //通过依赖查找，实例化对象(Tomcat BeanFactory setter 方法的执行，仅支持简单类型)
        componentNames.forEach(name->componentsMap.put(name,lookupComponent(name)));
    }

    private List<String> listAllComponentNames() {
        return listComponentNames("/");
    }

    protected List<String> listComponentNames(String path) {
        return executeInContext(context->{
            NamingEnumeration<NameClassPair> e = executeInContext(context,ctx->ctx.list(path),true);

            //目录 - Context
            //节点 -
            if (e == null){
                //当前 JNDI名称下没有子节点
                return Collections.emptyList();
            }
            //装载文件路径
            List<String> fullNames = new LinkedList<>();
            while (e.hasMoreElements()){
                NameClassPair element = e.nextElement();
                String clazzName = element.getClassName();
                Class<?> targetClass = clazzLoader.loadClass(clazzName);
                if (Context.class.isAssignableFrom(targetClass)){
                    // 如果当前名称是目录（Context 实现类）的话，递归查找
                    fullNames.addAll(listComponentNames(element.getName()));
                }else {
                    // 否则，当前名称绑定目标类型的话，添加名称到集合中
                    String fullName = path.startsWith("/")?element.getName():path+"/"+element.getName();
                    fullNames.add(fullName);
                }
            }
            return fullNames;
        });

    }

    /**
     * 初始化组件（支持 Java 标准 Commons Annotation 生命周期）
     * <ol>
     *  <li>注入阶段 - {@link Resource}</li>
     *  <li>初始阶段 - {@link PostConstruct}</li>
     *  <li>销毁阶段 - {@link PreDestroy}</li>
     * </ol>
     */
    protected void  initializeComponents() {
        componentsMap.values().forEach(component -> {
            Class<?> componentClass = component.getClass();
            // 注入阶段 - {@link Resource}
            injectComponents(component, componentClass);
            // 初始阶段 - {@link PostConstruct}
            processPostConstruct(component, componentClass);
            // TODO 实现销毁阶段 - {@link PreDestroy}
            processPreDestroy();
        });
    }

    private void injectComponents(Object component, Class<?> componentClass) {
        //DeclaredFields获取所有类型的class  Fields-->public
        Stream.of(componentClass.getDeclaredFields())
                .filter(field -> {
                    int mods = field.getModifiers();
                    return !Modifier.isStatic(mods) &&
                            field.isAnnotationPresent(Resource.class);
                }).forEach(field -> {
            Resource resource = field.getAnnotation(Resource.class);
            String resourceName = resource.name();
            Object injectedObject = lookupComponent(resourceName);
            field.setAccessible(true);
            try {
                // 注入目标对象
                field.set(component, injectedObject);
            } catch (IllegalAccessException e) {
            }
        });
    }

    private void processPostConstruct(Object component, Class<?> componentClass) {
        Stream.of(componentClass.getMethods())
                .filter(method ->
                        !Modifier.isStatic(method.getModifiers()) &&      // 非 static
                                method.getParameterCount() == 0 &&        // 没有参数
                                method.isAnnotationPresent(PostConstruct.class) // 标注 @PostConstruct
                ).forEach(method -> {
            // 执行目标方法
            try {
                method.invoke(component);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void processPreDestroy() {
        // TODO
    }

    /**
     * 在 Context 中执行，通过指定 ThrowableFunction 返回计算结果
     *
     * @param function ThrowableFunction
     * @param <R>      返回结果类型
     * @return 返回
     * @see ThrowableFunction#apply(Object)
     */
    protected <R>R executeInContext(ThrowableFunction<Context,R> function){
        return executeInContext(function, false);
    }

    /**
     * 在 Context 中执行，通过指定 ThrowableFunction 返回计算结果
     *
     * @param function         ThrowableFunction
     * @param ignoredException 是否忽略异常
     * @param <R>              返回结果类型
     * @return 返回
     * @see ThrowableFunction#apply(Object)
     */
    protected <R> R executeInContext(ThrowableFunction<Context, R> function, boolean ignoredException) {
        return executeInContext(this.context, function, ignoredException);
    }

    private <R> R executeInContext(Context context, ThrowableFunction<Context, R> function,
                                   boolean ignoredException) {
        R result = null;
        try {
            result = ThrowableFunction.execute(context, function);
        } catch (Throwable e) {
            if (ignoredException) {
                logger.warning(e.getMessage());
            } else {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
    /**
    *内部使用，增加保护级别
    * */
    protected <C> C lookupComponent(String name) {
        return executeInContext(context -> (C) context.lookup(name));
    }
    /**
     * 通过名称进行依赖查找
     *
     * @param name
     * @param <C>
     * @return
     */
    public <C>C getComponent(String name){
       /* C component = null;
        try {
            component=(C)context.lookup(name);
        }catch (NamingException ex){
            throw new NoSuchElementException(name);
        }
        return component;*/

        return (C)componentsMap.get(name);
    }

    /**
     * 获取所有的组件名称
     *
     * @return
     */
    public List<String> getComponentNames() {
        return new ArrayList<>(componentsMap.keySet());
    }

    private static void close(Context context){
        if (context!=null){
            ThrowableAction.execute(context::close);
        }
    }

    public void destroy(){
        if (this.context!=null){
            try{
                context.close();
            }catch (NamingException ex){
                throw new RuntimeException(ex);
            }
        }
    }
//    /**
//     * 传递ServletContext
//     * */
//    public static void setServletContext(ServletContext context){
//        ComponentContext.servletContext=context;
//    }

    /**
     * 获取ComponentContext
     * */
    public static ComponentContext getInstance(){
        return (ComponentContext) servletContext.getAttribute(CONTEXT_NAME);
    }

}
