package org.geektime.cache.file;

import org.geektime.cache.AbstractCache;

import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import java.io.File;

/**
 * File-System no-thread-safe {@link javax.cache.Cache} implementation
 */
public class FileSystemCache<K,V> extends AbstractCache<K,V> {
    
    private final File cacheDirectory;
    
    protected FileSystemCache(CacheManager cacheManager, String cacheName, Configuration<K, V> configuration){
        super(cacheManager,cacheName, configuration);
        cacheDirectory = new File(cacheManager.getURI().getPath());
    }
    
    
    @Override
    protected boolean containsEntry(K key) throws CacheException, ClassCastException{
        File entryFile = entryFile(key);
        return  entryFile.exists();
    }

    private File entryFile(K key) {
    }
}
