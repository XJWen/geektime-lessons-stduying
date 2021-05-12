package org.geektimes.cache.file;

import org.geektimes.cache.AbstractCache;
import org.geektimes.cache.ExpirableEntry;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import java.io.File;
import java.util.Set;

public class FileSystemCache <K,V> extends AbstractCache<K,V> {

    private final File cacheDirectory;

    protected FileSystemCache(CacheManager cacheManager, String cacheName, Configuration<K, V> configuration) {
        super(cacheManager, cacheName, configuration);
        cacheDirectory = new File(cacheManager.getURI().getPath());
    }

    private File entryFile(K key){
        return new File(cacheDirectory,String.valueOf(key));
    }

    /**
     * Contains the {@link Cache.Entry} by the specified key or not.
     *
     * @param key the key of {@link Cache.Entry}
     * @return <code>true</code> if contains, or <code>false</code>
     * @throws CacheException     it there is a problem checking the mapping
     * @throws ClassCastException if the implementation is configured to perform
     *                            runtime-type-checking, and the key or value
     *                            types are incompatible with those that have been
     *                            configured for the {@link Cache}
     */
    @Override
    protected boolean containsEntry(K key) throws CacheException, ClassCastException {
        File entryFile = entryFile(key);
        return entryFile.exists();
    }

    /**
     * Get the {@link Cache.Entry} by the specified key
     *
     * @param key the key of {@link Entry}
     * @return the existed {@link Cache.Entry} associated with the given key
     * @throws CacheException     if there is a problem fetching the value
     * @throws ClassCastException if the implementation is configured to perform
     *                            runtime-type-checking, and the key or value
     *                            types are incompatible with those that have been
     *                            configured for the {@link Cache}
     */
    @Override
    protected ExpirableEntry<K, V> getEntry(K key) throws CacheException, ClassCastException {
        return null;
    }

    /**
     * Put the specified {@link javax.cache.Cache.Entry} into cache.
     *
     * @param entry The new instance of {@link Entry<K,V>} is created by {@link Cache}
     * @throws CacheException     if there is a problem doing the put
     * @throws ClassCastException if the implementation is configured to perform
     *                            runtime-type-checking, and the key or value
     *                            types are incompatible with those that have been
     *                            configured for the {@link Cache}
     */
    @Override
    protected void putEntry(ExpirableEntry<K, V> entry) throws CacheException, ClassCastException {

    }

    /**
     * Remove the specified {@link Cache.Entry} from cache.
     *
     * @param key the key of {@link Entry}
     * @return the removed {@link Cache.Entry} associated with the given key
     * @throws CacheException     if there is a problem doing the remove
     * @throws ClassCastException if the implementation is configured to perform
     *                            runtime-type-checking, and the key or value
     *                            types are incompatible with those that have been
     *                            configured for the {@link Cache}
     */
    @Override
    protected ExpirableEntry<K, V> removeEntry(K key) throws CacheException, ClassCastException {
        return null;
    }

    /**
     * Clear all {@link Cache.Entry enties} from cache.
     *
     * @throws CacheException if there is a problem doing the clear
     */
    @Override
    protected void clearEntries() throws CacheException {

    }

    /**
     * Get all keys of {@link Cache.Entry} in the {@link Cache}
     *
     * @return the non-null read-only {@link Set}
     */
    @Override
    protected Set<K> keySet() {
        return null;
    }
}
