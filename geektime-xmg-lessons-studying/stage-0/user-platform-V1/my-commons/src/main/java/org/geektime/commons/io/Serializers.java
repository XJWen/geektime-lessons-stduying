package org.geektime.commons.io;

import org.geektime.commons.util.PriorityComparator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.ServiceLoader.load;
import static org.geektime.commons.reflect.util.TypeUtils.resolveTypeArguments;

public class Serializers {

    private static final Map<Class<?>, List<Serializer>> typedSerializers = new HashMap<>();

    private final ClassLoader classLoader;

    public Serializers(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public Serializers(){this(Thread.currentThread().getContextClassLoader());}

    public void loadSpi(){
        for(Serializer serializer:load(Serializer.class)){
            List<Class<?>> typeArguments = resolveTypeArguments(serializer.getClass());
            Class<?> targetClass = typeArguments.isEmpty() ? Object.class : typeArguments.get(0);
            List<Serializer> serializers = typedSerializers.computeIfAbsent(targetClass,key->new LinkedList<>());
            serializers.add(serializer);
            serializers.sort(PriorityComparator.INSTANCE);
        }
    }

    public Serializer<?> getMostCompatible(Class<?> serializedType){
        Serializer<?> serializer = getHighestPriority(serializedType);
        if(serializer == null){
            serializer = getLowestPriority(Object.class);
        }
        return serializer;
    }
    /**
     * Get the highest priority instance of {@link Serializer} by the specified serialized type
     *
     * @param serializedType the type to be serialized
     * @param <S>            the type to be serialized
     * @return <code>null</code> if not found
     */
    public <S>Serializer<S> getHighestPriority(Class<S> serializedType) {
        List<Serializer<S>> serializers = get(serializedType);
        return serializers.isEmpty() ? null : serializers.get(0);
    }
    /**
     * Get the lowest priority instance of {@link Serializer} by the specified serialized type
     *
     * @param objectClass the type to be serialized
     * @param <S>            the type to be serialized
     * @return <code>null</code> if not found
     */
    private <S>Serializer<S> getLowestPriority(Class<S> objectClass) {
        List<Serializer<S>> serializers = get(objectClass);
        return serializers.isEmpty() ? null : serializers.get(serializers.size() - 1);
    }

    /**
     * Get all instances of {@link Serializer} by the specified serialized type
     *
     * @param serializedType the type to be serialized
     * @param <S>            the type to be serialized
     * @return non-null {@link List}
     */
    public <S> List<Serializer<S>> get(Class<S> serializedType) {
        return (List) typedSerializers.getOrDefault(serializedType, emptyList());
    }
}
