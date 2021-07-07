package org.geektime.cache;

import javax.cache.Cache;
import java.io.Serializable;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Expirable {@link Cache.Entry}
 *
 * @see Cache.Entry
 */
public class ExpirableEntry<K,V> implements Cache.Entry<K,V>, Serializable {

    private final K key;

    private V value;

    private long timestamp;

    private ExpirableEntry(K key, V value)throws NullPointerException {
        requireKeyNotNull(key);
        this.key = key;
        this.setValue(value);
        //default
        this.timestamp = Long.MAX_VALUE;
    }

    public static<K,V> ExpirableEntry<K,V> of(K key,V value){return new ExpirableEntry(key,value);}

    public static <K,V> ExpirableEntry<K,V> of(Map.Entry<K,V> entry){
        return new ExpirableEntry<>(entry.getKey(), entry.getValue());
    }

    public static<K> void requireKeyNotNull(K key) {
        requireNonNull(key,"The key must not be null.");
    }

    public void setValue(V value) {
        requireValueNotNull(value);
        this.value = value;
    }

    public static<V> void requireValueNotNull(V value) {
        requireNonNull(value,"The value must not be null.");
    }

    public static <V> void requireOldValueNotNull(V oldValue) {
        requireNonNull(oldValue, "The oldValue must not be null.");
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        T value = null;
        try {
            value = aClass.newInstance();
        }catch(InstantiationException|IllegalAccessException ex){
            throw new RuntimeException(ex);
        }

        return value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isExpired() { return getExpiredTime()<1;}

    private long getExpiredTime() {
        return getTimestamp() - System.currentTimeMillis();
    }

    public  boolean isEternal() { return Long.MAX_VALUE == getTimestamp(); }

    @Override
    public String toString() {
        return "ExpirableEntry{" +
                "key=" + key +
                ", value=" + value +
                ", timestamp=" + timestamp +
                '}';
    }

}
