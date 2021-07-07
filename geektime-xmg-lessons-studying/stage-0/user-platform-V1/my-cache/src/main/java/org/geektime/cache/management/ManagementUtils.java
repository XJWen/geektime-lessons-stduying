package org.geektime.cache.management;


import javax.cache.Cache;
import javax.cache.configuration.CompleteConfiguration;
import javax.cache.management.CacheMXBean;
import javax.management.*;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Cache JMX Utilities class
 *
 */
public abstract class ManagementUtils {

    public static CacheMXBean adaptCacheMXBean(CompleteConfiguration<?,?> configuration){
        return  new CacheMXBeanAdapter(configuration);
    }

    private static ObjectName createObjectName(Cache<?,?> cache,
                                               String type){
        Hashtable<String, String> properties = new Hashtable<String, String>();
        properties.put("type",type);
        properties.put("name",cache.getName());
        properties.put("uri",getUri(cache));
        properties.putAll(getProperties(cache));
        ObjectName objectName = null;
        try {
            objectName = new ObjectName("javax.cache",properties);
        }catch (MalformedObjectNameException e){
            throw new IllegalArgumentException(e);
        }
        return objectName;
    }

    private static String getUri(Cache<?,?> cache) {
        URI uri = cache.getCacheManager().getURI();
        try {
            return URLEncoder.encode(uri.toASCIIString(),"UTF-8");
        }catch (UnsupportedEncodingException e){
            throw  new IllegalStateException(e);
        }
    }

    private static Map<String, String> getProperties(Cache<?,?> cache) {
        Properties properties = cache.getCacheManager().getProperties();
        Map<String, String> map = new LinkedHashMap<>();
        for (String propertyName : properties.stringPropertyNames()){
            map.put(propertyName,properties.getProperty(propertyName));
        }
        return map;
    }

    public static void  registerMBeansIfRequired(Cache<?,?> cache,CacheStatistics cacheStatistics){
        CompleteConfiguration configuration = cache.getConfiguration(CompleteConfiguration.class);
        if (configuration.isManagementEnabled()){
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            registerCacheMXBeanIfRequired(cache,configuration,mBeanServer);
            registerCacheStatisticsMXBeanIfRequired(cache,configuration,mBeanServer,cacheStatistics);
        }
    }

    private static void registerCacheMXBeanIfRequired(Cache<?,?> cache, CompleteConfiguration configuration, MBeanServer mBeanServer) {
        ObjectName objectName = createObjectName(cache,"CacheConfiguration");
        registerMBean(objectName,adaptCacheMXBean(configuration),mBeanServer);
    }

    private static void registerMBean(ObjectName objectName, Object object, MBeanServer mBeanServer) {
        try {
            if (!mBeanServer.isRegistered(objectName)){
                mBeanServer.registerMBean(object,objectName);
            }
        } catch (NotCompliantMBeanException | InstanceAlreadyExistsException |MBeanRegistrationException e) {
            throw new IllegalStateException(e);
        }
    }

    private static void registerCacheStatisticsMXBeanIfRequired(Cache<?,?> cache, CompleteConfiguration configuration,
                                                                MBeanServer mBeanServer, CacheStatistics cacheStatistics) {
        if (configuration.isStatisticsEnabled()){
            ObjectName objectName = createObjectName(cache,"CacheStatistics");
            registerMBean(objectName,cacheStatistics,mBeanServer);
        }
    }

}
