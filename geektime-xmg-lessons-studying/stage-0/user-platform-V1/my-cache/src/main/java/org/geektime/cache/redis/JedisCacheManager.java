package org.geektime.cache.redis;

import org.geektime.cache.AbstractCacheManager;

import javax.cache.Cache;
import javax.cache.configuration.Configuration;

public class JedisCacheManager extends AbstractCacheManager {
    @Override
    public <K, V, C extends Configuration<K, V>> Cache<K, V> createCache(String s, C c) throws IllegalArgumentException {
        return null;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String s, Class<K> aClass, Class<V> aClass1) {
        return null;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String s) {
        return null;
    }

    @Override
    public Iterable<String> getCacheNames() {
        return null;
    }

    @Override
    public void destroyCache(String s) {

    }

    @Override
    public void enableManagement(String s, boolean b) {

    }

    @Override
    public void enableStatistics(String s, boolean b) {

    }

    @Override
    public void close() {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }
}
