package org.geektime.cache.integration;

import javax.cache.Cache;
import javax.cache.integration.CacheWriterException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Abstract {@link FallbackStorage} implementation.
 */
public abstract class AbstractFallbackStorage<K,V> implements FallbackStorage<K,V>{

    private final int priority;

    protected AbstractFallbackStorage(int priority) { this.priority = priority; }

    @Override
    public Map<K,V> loadAll(Iterable<? extends K> keys) {
        Map<K,V> result = new LinkedHashMap<>();
        for (K key : keys) {
            result.put(key,load(key));
        }
        return result;
    }

    @Override
    public void writeAll(Collection<Cache.Entry<? extends K, ? extends V>> entries) throws CacheWriterException {
        entries.forEach(this::write);
    }

    @Override
    public void deleteAll(Collection<?> keys) throws CacheWriterException {
        keys.forEach(this::delete);
    }

    @Override
    public int getPriority() { return priority; }

}
