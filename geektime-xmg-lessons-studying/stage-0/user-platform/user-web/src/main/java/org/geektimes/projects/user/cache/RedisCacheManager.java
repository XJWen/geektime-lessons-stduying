package org.geektimes.projects.user.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.support.AbstractCacheManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Redis {@link org.springframework.cache.CacheManager}
 * */
public class RedisCacheManager extends AbstractCacheManager {

    private final JedisPool jedisPool;

    public RedisCacheManager(String uri) {
        this.jedisPool = new JedisPool(uri);
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        // 确保接口不返回 null
        List<? extends Cache> caches = new LinkedList<>();
        prepareCaches(caches);
        return caches;
    }

    @Override
    protected Cache getMissingCache(String name) {
        Jedis jedis = jedisPool.getResource();
        return new RedisCache(name, jedis);
    }

    private void prepareCaches(List<? extends Cache> caches) {
    }

}
