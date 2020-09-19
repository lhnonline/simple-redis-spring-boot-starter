

## @Cache
- 默认已打开对cache的支持

## RedisTemplate<Object, Object>, StringRedisTemplate
- 可以注入以上两个template

## RedissonClient
- 可以注入RedissonClient完成分布式锁的使用

## RedisUtil
- 可以注入RedisUtil完成对reids的一些便捷操作

## 配置文件
配置文件参考RedisConnectionConfiguration这个类的进行编写目前只支持单redis
```yaml
spring:
  redis:
    host: 127.0.0.1
    password:
    lettuce:
      pool:
        max-active: 10
        max-idle: 8
        max-wait: -1ms
        min-idle: 2

```

## pom.xml
```xml
<dependency>
     <groupId>com.github.lhnonline</groupId>
     <artifactId>simple-redis-spring-boot-starter</artifactId>
     <version>1.0.0</version>
</dependency>
```