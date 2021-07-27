package com.mao.crowd.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * Author: Administrator
 * Date: 2021/7/20 22:21
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    private Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void testSet() {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();

        operations.set("apple","red");
    }

    @Test
    public void testExSet() {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();

        operations.set("banana","yellow",50, TimeUnit.SECONDS);
    }
}
