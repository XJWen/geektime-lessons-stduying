package org.geektime.cache.event;

import javax.cache.Cache;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.EventType;

import static java.util.Objects.requireNonNull;

/**
 * Generic {@link CacheEntryEvent}
 *
 * @param <K> the type of key
 * @param <V> the type of value
 */
public class GenericCacheEntryEvent<K,V> extends CacheEntryEvent<K,V> {

    private final K key;

    private final V oldValue;

    private final V newValue;

    /**
     * Constructs a cache entry event from a given cache as source
     *
     * @param source    the cache that originated the event
     * @param eventType the event type for this event
     * @param key       the key of {@link javax.cache.Cache.Entry}
     * @param oldValue  the old value of {@link javax.cache.Cache.Entry}
     * @param newValue     the current value of {@link javax.cache.Cache.Entry}
     */
    public GenericCacheEntryEvent(Cache source, EventType eventType, K key, V oldValue, V newValue) {
        super(source, eventType);
        requireNonNull(key, "The key must not be null!");
        requireNonNull(newValue, "The value must not be null!");
        this.key = key;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    private static <V, K> CacheEntryEvent<K,V> of(Cache source, EventType eventType, K key, Object oldValue, V value) {
        return new GenericCacheEntryEvent(source, eventType, key, oldValue, value);

    }

    public static <K,V> CacheEntryEvent<K,V> createdEvent(Cache source,K key,V value) {
        return  of(source, EventType.CREATED,key,null,value);
    }


    public static <K, V> CacheEntryEvent<K, V> updatedEvent(Cache source, K key, V oldValue, V value) {
        return of(source, EventType.UPDATED, key, oldValue, value);
    }

    public static <K, V> CacheEntryEvent<K, V> expiredEvent(Cache source, K key, V oldValue) {
        return of(source, EventType.EXPIRED, key, oldValue, oldValue);
    }

    public static <K, V> CacheEntryEvent<K, V> removedEvent(Cache source, K key, V oldValue) {
        return of(source, EventType.REMOVED, key, oldValue, oldValue);
    }


    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.newValue;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return getSource().getCacheManager().unwrap(aClass);
    }

    @Override
    public V getOldValue() {
        return this.oldValue;
    }

    @Override
    public boolean isOldValueAvailable() {
        return oldValue!=null;
    }

    @Override
    public String toString() {
        return "GenericCacheEntryEvent{" +
                "key=" + getKey() +
                ", oldValue=" + getOldValue() +
                ", value=" + getValue() +
                ", evenType=" + getEventType() +
                ", source=" + getSource().getName() +
                '}';
    }
}
