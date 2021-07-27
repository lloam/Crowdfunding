package com.mao.crowd.controller;

import com.mao.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * Author: Administrator
 * Date: 2021/7/20 22:47
 * Description: redis 服务提供者主启动类
 */
@RestController
public class RedisController {


    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private Logger logger = LoggerFactory.getLogger(RedisController.class);

    /**
     * 设置不带超时时间的 key
     * @param key
     * @param value
     * @return
     */
    @PostMapping("/set/redis/key/value/remote")
    ResultEntity<String> setRedisKeyValueRemote(
            @RequestParam("key") String key,
            @RequestParam("value") String value
    ) {
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();

            operations.set(key,value);

            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 设置带超时时间的 key
     * @param key
     * @param value
     * @param time
     * @param timeUnit
     * @return
     */
    @PostMapping("/set/redis/key/value/remote/with/timeout")
    ResultEntity<String> setRedisKeyValueRemoteWithTimeOut(
            @RequestParam("key") String key,
            @RequestParam("value") String value,
            @RequestParam("time") long time,
            @RequestParam("timeUnit") TimeUnit timeUnit
    ) {
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();

            operations.set(key,value,time,timeUnit);

            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 根据 key 获取 value
     * @param key
     * @return
     */
    @GetMapping("/get/redis/string/value/by/key/remote")
    ResultEntity<String> getRedisStringValueByKeyRemote(@RequestParam("key") String key) {
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();

            String value = (String) operations.get(key);

            return ResultEntity.successWithData(value);
        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed("系统繁忙，请重试！！");
        }
    }

    /**
     * 根据 key 删除 redis 上的数据
     * @param key
     * @return
     */
    @DeleteMapping("/remove/redis/key/remote")
    ResultEntity<String> removeRedisKeyRemote(@RequestParam("key") String key) {
        try {
            redisTemplate.delete(key);

            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed(e.getMessage());
        }
    }
}
