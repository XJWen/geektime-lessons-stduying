package org.geektime.cache.integration;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

public class CompositeFallbackStorage extends AbstractFallbackStorage<Object, Object>{

    private static final ConcurrentMap<ClassLoader, List<FallbackStorage>> fallbackStorageCache = new ConcurrentHashMap<>();

    private final List<FallbackStorage> fallbackStorages;

    public CompositeFallbackStorage() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public CompositeFallbackStorage(ClassLoader classLoader) {
        super(Integer.MIN_VALUE);
        this.fallbackStorages = fallbackStorageCache.computeIfAbsent(classLoader,this::loadFallbackStorages);
     }

     private List<FallbackStorage> loadFallbackStorages(ClassLoader classLoader){
        return stream(ServiceLoader.load(FallbackStorage.class,classLoader).spliterator(),false)
                .sorted(PRIORITY_COMPARATOR)
                .collect(toList());
     }

    /**
     * Destroy
     */
    @Override
    public void destroy() {
        fallbackStorages.forEach(FallbackStorage::destroy);
    }

    @Override
    public Object load(Object o) throws CacheLoaderException {
        Object result = null;
        for (FallbackStorage fallbackStorage : fallbackStorages){
            result = fallbackStorage.load(o);
            if (result != null){
                break;
            }
        }
        return result;
    }

    @Override
    public void write(Cache.Entry<?, ?> entry) throws CacheWriterException {
        fallbackStorages.forEach(fallbackStorage -> fallbackStorage.write(entry));
    }

    @Override
    public void delete(Object o) throws CacheWriterException {
        fallbackStorages.forEach(fallbackStorage -> fallbackStorage.delete(o));
    }
}
