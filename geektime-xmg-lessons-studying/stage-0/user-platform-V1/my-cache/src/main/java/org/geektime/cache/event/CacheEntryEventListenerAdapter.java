package org.geektime.cache.event;

import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.Factory;
import javax.cache.event.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static java.util.Collections.unmodifiableMap;

/**
 * The adapter of {@link ConditionalCacheEntryEventListener} based on {@link CacheEntryListenerConfiguration}
 *
 * @see javax.cache.configuration.CacheEntryListenerConfiguration
 * */
public class CacheEntryEventListenerAdapter<K,V> implements ConditionalCacheEntryEventListener<K,V> {

    private static  List<Object> eventTypesAndHandleMethodNames = asList(
            EventType.CREATED,"onCreated",
            EventType.UPDATED,"onUpdated",
            EventType.EXPIRED, "onExpired",
            EventType.REMOVED, "onRemoved"
    );

    private final CacheEntryListenerConfiguration<K,V> configuration;

    private final CacheEntryEventFilter<? extends K,? extends V> cacheEntryEventFilter;

    private final CacheEntryListener<? extends K, ? extends V> cacheEntryListener;

    private final Map<EventType, Method> eventTypeMethods;

    private final  Executor executor;

    public CacheEntryEventListenerAdapter(CacheEntryListenerConfiguration<K, V> configuration) {
        this.configuration = configuration;
        this.cacheEntryEventFilter = getCacheEntryEventFilter(configuration);
        this.cacheEntryListener = (CacheEntryListener<? extends K, ? extends V>) configuration.getCacheEntryListenerFactory().create();
        this.eventTypeMethods = determineEventTypeMethods(cacheEntryListener);
        this.executor = getExecutor(configuration);
    }

    private CacheEntryEventFilter<? extends K,? extends V> getCacheEntryEventFilter
            (CacheEntryListenerConfiguration<K,V> configuration) {
        Factory<CacheEntryEventFilter<? super K,? extends V>> factory =
                (Factory<CacheEntryEventFilter<? super K, ? extends V>>) configuration.getCacheEntryEventFilterFactory();
        CacheEntryEventFilter<? extends K,? extends V> filter = null;

        if (factory != null) {
            filter = (CacheEntryEventFilter<? extends K, ? extends V>) factory.create();
        }

        if (filter == null){
            filter = e -> true;
        }

        return filter;
    }

    private Map<EventType, Method> determineEventTypeMethods(CacheEntryListener<? extends K,? extends V> cacheEntryListener) {
        Map<EventType,Method> eventTypeMethods = new HashMap<>(EventType.values().length);
        Class<?> cacheEntryListenerClass = cacheEntryListener.getClass();
        for ( int i = 0; i < eventTypesAndHandleMethodNames.size();){
            EventType eventType = (EventType) eventTypesAndHandleMethodNames.get(i++);
            String handleMethodName =(String) eventTypesAndHandleMethodNames.get(i++);
            try {
                Method handleMethod = cacheEntryListenerClass.getMethod(handleMethodName,Iterable.class);
                if (handleMethod!=null){
                    eventTypeMethods.put(eventType,handleMethod);
                }
            }catch (NoSuchMethodException ignored){

            }

        }

        return unmodifiableMap(eventTypeMethods);

    }

    private Executor getExecutor(CacheEntryListenerConfiguration<K, V> configuration) {
        Executor executor = null;
        if (configuration.isSynchronous()){
            executor = Runnable::run;
        }else{
            executor = ForkJoinPool.commonPool();
        }
        return executor;
    }

    /**
     * Determines current listener supports the given {@link CacheEntryEvent} or not.
     *
     * @param event
     * @return The effect of returning true is that listener will be invoked
     * @throws CacheEntryListenerException
     * @see CacheEntryEventFilter#evaluate(CacheEntryEvent)
     */
    @Override
    public boolean supports(CacheEntryEvent<? extends K, ? extends V> event){

//        return supportsEventType(event) && cacheEntryEventFilter.evaluate(event);
        return supportsEventType(event);
    }

    private boolean supportsEventType(CacheEntryEvent<? extends K,? extends V> event) {
        return getSupportedEventTypes().contains(event.getEventType());
    }

    /**
     * Called after one entry was raised by some event.
     *
     * @param event some event
     * @see CacheEntryCreatedListener
     * @see CacheEntryUpdatedListener
     * @see CacheEntryRemovedListener
     * @see CacheEntryExpiredListener
     */
    @Override
    public void onEvent(CacheEntryEvent<? extends K, ? extends V> event) {
        if (!supports(event)){
            return;
        }

        EventType eventType = event.getEventType();
        Method  handleMethod = eventTypeMethods.get(eventType);

        executor.execute(() -> {
                try {
                    handleMethod.invoke(cacheEntryListener,singleton(event));
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        );

    }

    /**
     * Get the supported {@link EventType event types}
     *
     * @return non-null
     */
    @Override
    public Set<EventType> getSupportedEventTypes() {
        return eventTypeMethods.keySet();
    }

    /**
     * The {@link Executor} is used to dispatch the {@link CacheEntryEvent}
     *
     * @return non-null
     * @see CacheEntryListenerConfiguration#isSynchronous()
     */
    @Override
    public Executor getExecutor() {
        return executor;
    }

    @Override
    public int hashCode() {
        return configuration.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CacheEntryEventListenerAdapter)) {
            return false;
        }
        CacheEntryEventListenerAdapter another = (CacheEntryEventListenerAdapter) object;
        return this.configuration.equals(another.configuration);
    }
}
