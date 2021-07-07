package org.geektime.inject;

import org.geektime.function.ThrowableFunction;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import static org.geektime.function.ThrowableFunction.execute;

public abstract class AbstractComponentRepository implements ComponentRepository{

    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * 本地组件缓存
     * */
    private Map<String, Object> componentsCaches =new LinkedHashMap<>();

    /**
     * 通过名称查找组件对象
     *
     * @param name 组件名称
     * @return 如果找不到返回, <code>null</code>
     */
    @Override
    public <C> C getComponent(String name) {
        return (C)componentsCaches.computeIfAbsent(name,this::doGetComponent);
    }

    protected abstract Object doGetComponent(String s);

    /**
     * 获取所有的组件名称
     *
     * @return
     */
    @Override
    public Set<String> getComponentNames() {
        return componentsCaches.isEmpty()?componentsCaches.keySet():listComponentNames();
    }

    protected abstract Set<String> listComponentNames();

    /**
     * 通过指定 ThrowableFunction 返回计算结果
     *
     * @param argument         Function's argument
     * @param function         ThrowableFunction
     * @param ignoredException 是否忽略异常
     * @param <R>              返回结果类型
     * @return 返回
     * @see ThrowableFunction#apply(Object)
     */
    protected <T,R> R executeInContext(T argument, ThrowableFunction<T,R> function,boolean ignoredException){
        R result = null;

        try {
            result = execute(argument,function);
        }catch (Throwable e){
            if(ignoredException){
                logger.warning(e.getMessage());
            }else{
                throw new RuntimeException(e);
            }
        }

        return  result;
    }

}
