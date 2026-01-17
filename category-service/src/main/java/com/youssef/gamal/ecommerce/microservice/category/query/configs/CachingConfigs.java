package com.youssef.gamal.ecommerce.microservice.category.query.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class CachingConfigs {
	
	public static final String CATEGORY_VIEW_CACHE_NAME = "categoryViewCache";


	@Bean
	public ObjectMapper redisObjectMapper() {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.findAndRegisterModules();
	    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	    mapper.activateDefaultTyping(
	        mapper.getPolymorphicTypeValidator(),
	        ObjectMapper.DefaultTyping.NON_FINAL
	    );
	    return mapper; // ✅ type info enabled only for Redis
	}

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // Use GenericJackson2JsonRedisSerializer which handles type information automatically
        GenericJackson2JsonRedisSerializer genericSerializer = new GenericJackson2JsonRedisSerializer(redisObjectMapper());

        // Set serializers
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(genericSerializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(genericSerializer);

        template.afterPropertiesSet();
        return template;
    }

    @Bean
    @Primary
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // Use GenericJackson2JsonRedisSerializer which handles type information automatically
        GenericJackson2JsonRedisSerializer genericSerializer = new GenericJackson2JsonRedisSerializer(redisObjectMapper());

        // Default configuration for all caches
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1)) // Default TTL
                .disableCachingNullValues() // Don't cache null values
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                    .fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                    .fromSerializer(genericSerializer));

        // Specific configuration for categoryViewCache
        RedisCacheConfiguration categoryViewConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30)) // CategoryView cache for 30 minutes
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                    .fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                    .fromSerializer(genericSerializer));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultConfig)
                .withCacheConfiguration(CachingConfigs.CATEGORY_VIEW_CACHE_NAME, categoryViewConfig)
                // Add more specific cache configurations here if needed
                // .withCacheConfiguration("otherCache", otherCacheConfig)
                .build();
    }
}