package io.cache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;

@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    @Autowired
    private RedisConnectionFactory factory;

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(){
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(factory);

        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setKeySerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);

        return redisTemplate;
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator(){
        return (o,method,objects) ->{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(o.getClass().getName()).append(".");
            stringBuilder.append(method.getName()).append(".");
            for (Object object:objects){
                stringBuilder.append(object.toString());
            }
            return stringBuilder.toString();
        };
    }

    @Bean
    @Override
    public CacheResolver cacheResolver(){return new SimpleCacheResolver(cacheManager());
    }

    @Bean
    @Override
    public CacheManager cacheManager(){
        RedisCacheConfiguration cacheConfiguration = defaultCacheConfig()
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        return RedisCacheManager.builder(factory).cacheDefaults(cacheConfiguration).build();
    }

    @Bean
    @Override
    public CacheErrorHandler errorHandler(){
        // 用于捕获从Cache中进行CRUD时的异常的回调处理器。
        return  new SimpleCacheErrorHandler();
    }
}
