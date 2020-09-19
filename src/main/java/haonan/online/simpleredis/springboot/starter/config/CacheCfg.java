package haonan.online.simpleredis.springboot.starter.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;

/**
 * @author luohaonan
 * @version 1.0.0 @Description
 * @createTime 2020-09-19
 */
@Slf4j
@Component
@EnableCaching
public class CacheCfg extends CachingConfigurerSupport {

  @Resource private RedisConnectionFactory factory;

  @Override
  @Bean
  public KeyGenerator keyGenerator() {
    return (o, method, objects) -> {
      StringBuilder sb = new StringBuilder();
      sb.append(o.getClass().getName()).append("_");
      sb.append(method.getName()).append("_");
      for (Object obj : objects) {
        sb.append(obj.toString());
      }
      return sb.toString();
    };
  }

  @Bean
  @Override
  public CacheResolver cacheResolver() {
    return new SimpleCacheResolver(cacheManager());
  }

  @Bean
  @Override
  public CacheErrorHandler errorHandler() {
    return new SimpleCacheErrorHandler();
  }

  @Bean
  @Override
  public CacheManager cacheManager() {
    RedisCacheConfiguration cacheConfiguration =
        defaultCacheConfig()
            .disableCachingNullValues()
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    new FastJsonRedisSerializer<>(Object.class)));
    return RedisCacheManager.builder(factory).cacheDefaults(cacheConfiguration).build();
  }
}
