package com.mantas.mvc.deprecated.probe.saver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 使用Redis存储数据
 */
@Slf4j
public class RedisDeprecatedProbeSaver extends AbstractDeprecatedProbeSaver {

    private static final int duration = 5 * 60 * 1000;

    private StringRedisTemplate redisTemplate;
    private String redisKey;
    private int listLength;

    private long preTrimTime = System.currentTimeMillis();

    /**
     *
     * @param key: 存储到redis的list的key
     * @param redisTemplate
     * @param length: 存储的list的长度, 程序间隔5分钟修剪list的长度, 如果传入的值 <=0, 则重置为 100
     */
    public RedisDeprecatedProbeSaver(String key, StringRedisTemplate redisTemplate, int length) {
        this.redisKey = key;
        if (length <= 0) {
            length = 100;
        }
        listLength = length;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void doSave(DeprecatedMethodData data) {
        log.info("push deprecated method data: {}", data.toString());
        redisTemplate.opsForList().leftPush(redisKey, data.toString());

        if (System.currentTimeMillis() - preTrimTime > duration) {
            redisTemplate.opsForList().trim(redisKey, 0, listLength);
            preTrimTime = System.currentTimeMillis();
        }

    }
}
