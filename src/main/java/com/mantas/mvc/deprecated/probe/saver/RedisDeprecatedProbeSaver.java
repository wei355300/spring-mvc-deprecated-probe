package com.mantas.mvc.deprecated.probe.saver;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 使用Redis存储数据
 */
public class RedisDeprecatedProbeSaver extends AbstractDeprecatedProbeSaver {

    private static final String REDIS_KEY = "deprecated:spring:method:used";

    private StringRedisTemplate redisTemplate;

    public RedisDeprecatedProbeSaver(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void doSave(DeprecatedMethodData data) {
        // todo
        redisTemplate.opsForSet().add(REDIS_KEY, data.toString());
    }
}
