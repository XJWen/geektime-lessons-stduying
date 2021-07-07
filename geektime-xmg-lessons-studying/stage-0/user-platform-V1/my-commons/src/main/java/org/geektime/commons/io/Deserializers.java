package org.geektime.commons.io;

import org.geektime.commons.util.PriorityComparator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.ServiceLoader.load;
import static org.geektime.commons.reflect.util.TypeUtils.resolveTypeArguments;

public class Deserializers {

    private final Map<Class<?>, List<Deserializer>> typedDeserializers = new HashMap<>();

    private final ClassLoader classLoader;

    public Deserializers(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public Deserializers(){
        this(Thread.currentThread().getContextClassLoader());
    }

    public void loadSpi(){
        for (Deserializer deserializer:load(Deserializer.class)){
            List<Class<?>> typeArguments = resolveTypeArguments(deserializer.getClass());
            Class<?> targetClass = typeArguments.isEmpty()?Object.class :typeArguments.get(0);
            List<Deserializer> deserializers = typedDeserializers.computeIfAbsent(targetClass,key->new LinkedList<>());
            deserializers.add(deserializer);
            deserializers.sort(PriorityComparator.INSTANCE);
        }
    }

    /**
     * Get the most compatible instance of {@link Deserializer} by the specified deserialized type
     *
     * @param deserializedType the type to be deserialized
     * @return <code>null</code> if not found
     */
    public Deserializer<?> getMostCompatible(Class<?> deserializedType){
        Deserializer<?> deserializer = getHighestPriority(deserializedType);
        if (deserializer == null){
            deserializer = getLowestPriority(Object.class);
        }
        return deserializer;
    }
    /**
     * Get the highest priority instance of {@link Deserializer} by the specified deserialized type
     *
     * @param deserializedType the type to be deserialized
     * @param <T>              the type to be serialized
     * @return <code>null</code> if not found
     */
    public <T>Deserializer<T> getHighestPriority(Class<?> deserializedType) {
        List<Deserializer<T>> serializers = get(deserializedType);
        return serializers.isEmpty() ? null : serializers.get(0);
    }

    /**
     * Get the lowest priority instance of {@link Deserializer} by the specified deserialized type
     *
     * @param deserializedType the type to be deserialized
     * @param <T>              the type to be serialized
     * @return <code>null</code> if not found
     */
    public <T> Deserializer<T> getLowestPriority(Class<?> deserializedType) {
        List<Deserializer<T>> serializers = get(deserializedType);
        return serializers.isEmpty() ? null : serializers.get(0);
    }

    /**
     * Get all instances of {@link Deserializer} by the specified deserialized type
     *
     * @param deserializedType the type to be deserialized
     * @param <T>              the type to be serialized
     * @return non-null {@link List}
     */
    private <T> List<Deserializer<T>> get(Class<?> deserializedType) {
        return (List)typedDeserializers.getOrDefault(deserializedType,emptyList());
    }

}
