package org.geektime.commons.reflect.util;

import java.lang.annotation.Annotation;

public abstract class ClassUtils {
    private ClassUtils() {}

    public static <A extends Annotation> A findAnnotation(Class<?>type,Class<A> annotationType) {
        if (Object.class.equals(type)||type==null){
            //Object作为父类的最终递归终结点
            return null;
        }
        A annotation = type.getAnnotation(annotationType);
        if (annotation==null){

            for(Class<?> interfaceType : type.getInterfaces()){
                //find the annotation from the super interfaces
                //寻找类的接口是否含注解
                annotation = interfaceType.getAnnotation(annotationType);
                if (annotation!=null){
                    break;
                }
            }
        }

        if (annotation == null){
            // find the annotation from the super class recursively
            //寻找类的父类是否含注解
            annotation = findAnnotation(type.getSuperclass(),annotationType);
        }
        return annotation;
    }
}
