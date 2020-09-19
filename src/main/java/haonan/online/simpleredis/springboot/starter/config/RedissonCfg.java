package haonan.online.simpleredis.springboot.starter.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author luohaonan
 * @version 1.0.0 @Description
 * @createTime 2020-09-19
 */
@Setter
@Getter
@Component
@SuppressWarnings("all")
@Slf4j
public class RedissonCfg {

  @Resource RedisProperties redisProperties;

  @Bean
  public RedissonClient redissonClient(Config config) {
    return Redisson.create(config);
  }

  @Bean
  public Config config() {

    Config config = new Config();

    /** 单redis */
    String host = redisProperties.getHost();
    if (!StringUtils.isEmpty(host)) {
      Integer port = redisProperties.getPort();
      String password = redisProperties.getPassword();
      int database = redisProperties.getDatabase();
      String protocol = redisProperties.isSsl() ? "rediss" : "redis";
      SingleServerConfig ssc =
          config
              .useSingleServer()
              .setAddress(protocol + "://" + host + ":" + port)
              .setDatabase(database);
      if (!StringUtils.isEmpty(password)) {
        ssc.setPassword(password);
      }
    }

    /** 主从 todo */

    /** sentinel todo */
    return config;
  }
}
