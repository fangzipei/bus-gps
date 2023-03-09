//package com.zonefun.backend.redis;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.serializer.*;
//
//import java.time.Duration;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
///**
// * @ClassName RedisTemplateConfig
// * @Description redis template配置
// * @Date 2022/6/27 9:15
// * @Author ZoneFang
// */
//@EnableCaching
//@Configuration
//@Slf4j
//public class RedisTemplateConfig extends CachingConfigurerSupport {
//
//    public static final String REDIS_KEY_PREFIX = "algorithm";
//
//    @Autowired
//    private LettuceConnectionFactory lettuceConnectionFactory;
//
//    @Bean
//    @Override
//    public KeyGenerator keyGenerator() {
//        return (target, method, params) -> {
//            StringBuilder sb = new StringBuilder();
//            sb.append(target.getClass().getName());
//            sb.append(method.getName());
//            for (Object obj : params) {
//                sb.append(obj.toString());
//            }
//            return sb.toString();
//        };
//    }
//
//    /**
//     * 缓存管理器
//     */
//    @Bean
//    @Override
//    public CacheManager cacheManager() {
//        // GenericJackson2JsonRedisSerializer cacheConfiguration
//        RedisCacheConfiguration genericCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//                .serializeKeysWith(
//                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair
//                        .fromSerializer(new GenericJackson2JsonRedisSerializer()))
//                .entryTtl(Duration.ofDays(1));
//
//        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder
//                .fromConnectionFactory(lettuceConnectionFactory);
//
//        // 设置初始化的缓存空间set集合
//        Set<String> cacheNames = new HashSet<>();
//        cacheNames.add(REDIS_KEY_PREFIX);
//
//        // 对每个缓存空间应用不同的配置
//        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
//        configMap.put(REDIS_KEY_PREFIX, genericCacheConfiguration.entryTtl(Duration.ofMinutes(10)));
//
//        builder.initialCacheNames(cacheNames).withInitialCacheConfigurations(configMap);
//        return builder.build();
//    }
//
//    @Bean
//    @Primary
//    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
//        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
//        serializer.setObjectMapper(om);
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        log.info("redisTemplate::lettuceConnectionFactory.getValidateConnection: {}",
//                lettuceConnectionFactory.getValidateConnection());
//        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
//        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
//        redisTemplate.setKeySerializer(stringSerializer);
//        redisTemplate.setValueSerializer(serializer);
//        redisTemplate.setHashKeySerializer(stringSerializer);
//        redisTemplate.setHashValueSerializer(serializer);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//
//    /**
//     * 支持保存 Null 值
//     *
//     * @param lettuceConnectionFactory
//     * @return
//     */
//    @Bean("genericRedisTemplate")
//    public RedisTemplate<String, Object> genericRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
//        log.info("genericRedisTemplate::lettuceConnectionFactory.getValidateConnection: {}",
//                lettuceConnectionFactory.getValidateConnection());
//        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
//        redisTemplate.setKeySerializer(stringSerializer);
//        redisTemplate.setHashKeySerializer(stringSerializer);
//
//        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
//        redisTemplate.setValueSerializer(serializer);
//        redisTemplate.setHashValueSerializer(serializer);
//
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//
//    /**
//     * StringRedisTemplate配置
//     *
//     * @param lettuceConnectionFactory
//     * @return
//     */
//    @Bean
//    public StringRedisTemplate stringRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
//        StringRedisTemplate template = new StringRedisTemplate();
//        Jackson2JsonRedisSerializer<?> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        template.setValueSerializer(serializer);
//        log.info("stringRedisTemplate::lettuceConnectionFactory.getValidateConnection: {}",
//                lettuceConnectionFactory.getValidateConnection());
//        template.setConnectionFactory(lettuceConnectionFactory);
//        return template;
//    }
//}
