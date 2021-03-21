package org.geektimes.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JdkProxyFcatory {

    private static Map<String,Object> iocMap = new ConcurrentHashMap<>();

    public static <T> T createObject(Class clazz,InvocationHandler handler){
        if (!iocMap.containsKey(clazz.getName())){
            iocMap.put(clazz.getName(),newProxy(clazz,handler));
        }
        return (T) iocMap.get(clazz.getName());
    }

    private static <T> T newProxy(Class clazz,InvocationHandler handler){
        ClassLoader classLoader = JdkProxyFcatory.class.getClassLoader();
        Class[] classList = new Class[]{clazz};
        return (T) Proxy.newProxyInstance(classLoader,classList,handler);
    }

}
