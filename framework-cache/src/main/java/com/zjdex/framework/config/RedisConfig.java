package com.zjdex.framework.config;

import com.alibaba.fastjson.parser.ParserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;

/**
 * @author: lindj
 * @date: 2018/6/1 13:38
 * @description: redis配置
 */
@Configuration
@EnableCaching
public class RedisConfig  extends CachingConfigurerSupport {

    @Bean
    public KeyGenerator keyGenerators() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    public RedisConfig() {
        super();
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer);
        RedisCacheConfiguration defaultCacheConfig=RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);

        //序列化方式3
        //Jackson2JsonRedisSerializer serializer=new Jackson2JsonRedisSerializer(Object.class);
        //RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(serializer);
        //RedisCacheConfiguration defaultCacheConfig=RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);

        defaultCacheConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(100));
        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
        ParserConfig.getGlobalInstance().addAccept("com.zjdex.");
        return cacheManager;
    }

    @Bean
    public RedisSerializer fastJson2JsonRedisSerializer() {
        return new FastJsonRedisSerializer<Object>(Object.class);
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        // 使用fastjson序列化
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        ParserConfig.getGlobalInstance().addAccept("com.zjdex.");
        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }


}
