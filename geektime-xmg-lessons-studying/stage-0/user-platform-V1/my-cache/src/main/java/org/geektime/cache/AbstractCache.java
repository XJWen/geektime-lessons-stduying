package org.geektime.cache;

import org.geektime.cache.event.CacheEntryEventPublisher;
import org.geektime.cache.integration.CompositeFallbackStorage;
import org.geektime.cache.integration.FallbackStorage;
import org.geektime.cache.management.CacheStatistics;
import org.geektime.cache.management.DummyCacheStatistics;
import org.geektime.cache.management.SimpleCacheStatistics;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.CompleteConfiguration;
import javax.cache.configuration.Configuration;
import javax.cache.configuration.Factory;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;
import javax.cache.expiry.EternalExpiryPolicy;
import javax.cache.expiry.ExpiryPolicy;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheWriter;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;

import static org.geektime.cache.ExpirableEntry.requireKeyNotNull;
import static org.geektime.cache.configuration.ConfigurationUtils.mutableConfiguration;
import static org.geektime.cache.management.ManagementUtils.registerMBeansIfRequired;

/**
 * The abstract non-thread-safe implementation of {@link Cache}
 *
 */
public abstract class AbstractCache<K,V> implements Cache<K, V> {

    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    private  final CacheManager cacheManager;

    private final  String cacheName;

    private final MutableConfiguration<K, V> configuration;

    private final ExpiryPolicy expiryPolicy;

    private final CacheLoader<K,V> cacheLoader;

    private  final CacheWriter<K, V> cacheWriter;

    private final FallbackStorage defaultFallbackStorage;

    private final CacheEntryEventPublisher entryEventPublisher;

    private final CacheStatistics cacheStatistics;

    private final Executor executor;

    private volatile boolean closed = false;

    protected AbstractCache(CacheManager cacheManager, String cacheName, Configuration<K, V> configuration) {
        this.cacheManager = cacheManager;
        this.cacheName = cacheName;
        this.configuration = mutableConfiguration(configuration);
        this.expiryPolicy = resolveExpiryPolicy(getConfiguration());
        this.defaultFallbackStorage = new CompositeFallbackStorage(getClassLoader());
        this.cacheLoader = resolveCacheLoader(getConfiguration(), getClassLoader());
        this.cacheWriter = resolveCacheWriter(getConfiguration(), getClassLoader());
        this.entryEventPublisher = new CacheEntryEventPublisher();
        this.cacheStatistics = resolveCacheStatistic();
        this.executor = ForkJoinPool.commonPool();
        registerCacheEntryListenersFromConfiguration();
        registerMBeansIfRequired(this, cacheStatistics);
    }

    private ExpiryPolicy resolveExpiryPolicy(CompleteConfiguration<?, ?> configuration) {
        Factory<ExpiryPolicy> expiryPolicyFactory = configuration.getExpiryPolicyFactory();
        if (expiryPolicyFactory==null) {
            expiryPolicyFactory = EternalExpiryPolicy::new;
        }
        return expiryPolicyFactory.create();
    }

    // Operations of CompleteConfiguration
    protected final CompleteConfiguration<K, V> getConfiguration(){
        return this.configuration;
    }

    // Other Operations
    protected ClassLoader getClassLoader(){
        return getCacheManager().getClassLoader();
    }

    private CacheLoader<K, V> resolveCacheLoader(CompleteConfiguration<K, V> configuration, ClassLoader classLoader){
        Factory<CacheLoader<K, V>> cacheLoaderFactory = configuration.getCacheLoaderFactory();
        CacheLoader<K,V> cacheLoader = null;

        if (cacheLoaderFactory!=null){
            cacheLoader = cacheLoaderFactory.create();
        }

        if (cacheLoader == null){
            cacheLoader = this.defaultFallbackStorage;
        }

        return cacheLoader;
    }

    private CacheWriter<K, V> resolveCacheWriter(CompleteConfiguration<K, V> configuration, ClassLoader classLoader) {
        Factory<CacheWriter<? super K,? super V>> cacheWriterFactory = configuration.getCacheWriterFactory();
        CacheWriter<K,V> cacheWriter = null;
        if (cacheWriterFactory != null){
            cacheWriter = (CacheWriter<K,V>)cacheWriterFactory.create();
        }

        if (cacheWriter == null){
            cacheWriter = this.defaultFallbackStorage;
        }

        return cacheWriter;
    }

    private CacheStatistics resolveCacheStatistic() {
        return isStatisticsEnabled()?
                new SimpleCacheStatistics() : DummyCacheStatistics.INSTANCE;
    }

    private final boolean isStatisticsEnabled() {
        return configuration.isStatisticsEnabled();
    }

    // Operations of CacheEntryEvent and CacheEntryListenerConfiguration
    private void registerCacheEntryListenersFromConfiguration() {
        this.configuration.getCacheEntryListenerConfigurations()
                .forEach(this::registerCacheEntryListener);
    }

    /**
     * Determines if the {@link Cache} contains an entry for the specified key.
     * <p>
     * More formally, returns <tt>true</tt> if and only if this cache contains a
     * mapping for a key <tt>k</tt> such that <tt>key.equals(k)</tt>.
     * (There can be at most one such mapping.)</p>
     * <p>
     * If the cache is configured read-through the associated {@link CacheLoader}
     * is not called. Only the cache is checked.
     * </p>
     * <p>
     * Current method calls the methods of {@link ExpiryPolicy}:
     * <ul>
     *     <li>No {@link ExpiryPolicy#getExpiryForCreation}</li>
     *     <li>No {@link ExpiryPolicy#getExpiryForAccess}</li>
     *     <li>No {@link ExpiryPolicy#getExpiryForUpdate}</li>
     * </ul>
     *
     * @param key key whose presence in this cache is to be tested.
     * @param key the specified key
     * @return
     * @throws NullPointerException  if key is null
     * @throws IllegalStateException if the cache is {@link #isClosed()}
     * @throws CacheException        it there is a problem checking the mapping
     * @throws ClassCastException    if the implementation is configured to perform
     *                               runtime-type-checking, and the key or value
     *                               types are incompatible with those that have been
     *                               configured for the {@link Cache}
     */
    @Override
    public boolean containsKey(K key){
        assertNotClosed();
        return containsEntry(key);
    }

    private void assertNotClosed(){
        if(isClosed()){
            throw new IllegalStateException("Current cache has been closed! No operation should be executed.");
        }
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
    protected  abstract  boolean containsEntry(K key) throws  CacheException,ClassCastException;

    /**
     * Gets an entry from the cache.
     * <p>
     * If the cache is configured to use read-through, and get would return null
     * because the entry is missing from the cache, the Cache's {@link CacheLoader}
     * is called in an attempt to load the entry.
     * <p>
     * Current method calls the methods of {@link ExpiryPolicy}:
     * <ul>
     *     <li>No {@link ExpiryPolicy#getExpiryForCreation} ({@link #loadValue(Object, boolean) unless read-though caused a load)}</li>
     *     <li>Yes {@link ExpiryPolicy#getExpiryForAccess}</li>
     *     <li>No {@link ExpiryPolicy#getExpiryForUpdate}</li>
     * </ul>
     *
     * @param key the key whose associated value is to be returned
     * @param key the specified key
     * @return the element, or null, if it does not exist.
     * @throws IllegalStateException if the cache is {@link #isClosed()}
     * @throws NullPointerException  if the key is null
     * @throws CacheException        if there is a problem fetching the value
     * @throws ClassCastException    if the implementation is configured to perform
     *                               runtime-type-checking, and the key or value
     *                               types are incompatible with those that have been
     *                               configured for the {@link Cache}
     */
    @Override
    public V get(K key){
        assertNotClosed();
        requireKeyNotNull(key);
        ExpirableEntry<K,V> entry = null;
        V value = null;
        long startTime = System.currentTimeMillis();
        try {
            entry = getEntry(key);
            if (handleExpiryPolicyForAccess(entry)){
                return null;
            }

            if (entry == null && isReadThrough()){
                value = loadValue(key,true);
            }else{
                value = getValue(entry);
            }
        }catch (Throwable e) {
            logger.severe(e.getMessage());
        }finally {
            if (value!= null)
            {
                cacheStatistics.cacheHits();
            }
            cacheStatistics.cacheGets();
            cacheStatistics.cacheGetsTime(System.currentTimeMillis() - startTime);
        }
        return value;
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
    protected abstract ExpirableEntry<K,V> getEntry(K key)throws CacheException, ClassCastException;

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
    protected abstract void putEntry(ExpirableEntry<K, V> entry) throws CacheException, ClassCastException;

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
    protected abstract ExpirableEntry<K, V> removeEntry(K key) throws CacheException, ClassCastException;

    protected  boolean handleExpiryPolicyForAccess(ExpirableEntry<K,V> entry){
        return handleExpiryPolicy(entry,getExpiryForAccess(),true);
    }

    private  boolean handleExpiryPolicy(ExpirableEntry<K,V> entry, Duration duration, boolean removedExpiredEntry){

    }

}
