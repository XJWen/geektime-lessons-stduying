package org.geektime.commons.reflect.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.emptyList;

public abstract class TypeUtils {

    private TypeUtils() {}

    public static List<Class<?>> resolveTypeArguments(Class<?> targetClass) {
        List<Class<?>> typeArguments = emptyList();
        while(targetClass!=null){
            typeArguments = resolveTypeArgumentsFromInterfaces(targetClass);
            if (typeArguments.isEmpty()){
                break;
            }
            //查询父类类型
            Type superType = targetClass.getGenericSuperclass();
            if (superType instanceof ParameterizedType){
                typeArguments = resolveTypeArgumentsFromType(superType);
            }

            if (!typeArguments.isEmpty()){
                break;
            }
            // recursively
            targetClass = targetClass.getSuperclass();
        }
        return typeArguments;
    }

    private static List<Class<?>> resolveTypeArgumentsFromInterfaces(Class<?> type) {
        List<Class<?>> typeArguments = emptyList();
        for (Type superInterface : type.getGenericInterfaces()){
            //查询接口类型
            typeArguments = resolveTypeArgumentsFromType(superInterface);
            if (typeArguments != null&&!typeArguments.isEmpty()){
                break;
            }
        }
        return typeArguments;
    }

    private static List<Class<?>> resolveTypeArgumentsFromType(Type type) {
        List<Class<?>> typeArguments = emptyList();
        if (type instanceof ParameterizedType){
            typeArguments = new LinkedList<>();
            ParameterizedType parameterizedType = (ParameterizedType) type;
            if (parameterizedType.getRawType() instanceof Class){
                for (Type argument:parameterizedType.getActualTypeArguments()){
                    Class<?> typeArgument = asClass(argument);
                    if (typeArgument!=null){
                        typeArguments.add(typeArgument);
                    }
                }
            }
        }
        return typeArguments;
    }

    private static Class<?> asClass(Type typeArgument) {
        if (typeArgument instanceof Class){
            return (Class<?>) typeArgument;
        }else if (typeArgument instanceof TypeVariable){
            TypeVariable typeVariable = (TypeVariable) typeArgument;
            return  asClass(typeVariable.getBounds()[0]);
        }
        return null;
    }

}
