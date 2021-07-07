package org.geektime.cache.integration;


import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheWriter;
import java.util.Comparator;

/**
 * Fallback Storage that only extends {@link CacheLoader} and {@link CacheWriter}
 */
public interface FallbackStorage<K,V> extends CacheLoader<K, V>, CacheWriter<K,V> {

    Comparator<FallbackStorage> PRIORITY_COMPARATOR = new PriorityComparator();

    class PriorityComparator implements Comparator<FallbackStorage> {

        @Override
        public int compare(FallbackStorage o1,FallbackStorage o2){
            return Integer.compare(o2.getPriority(),o1.getPriority());
        }

    }

    /**
     * Get the priority of current {@link FallbackStorage}.
     *
     * @return the less value , the more priority.
     */
    int getPriority();

    /**
     * Destroy
     */
    void destroy();

}

