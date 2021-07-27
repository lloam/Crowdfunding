package com.mao.crowd.api;

import com.mao.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

/**
 * Author: Administrator
 * Date: 2021/7/20 22:35
 * Description: Redis 对 feign 暴露的 controller 接口
 */
@FeignClient("mao-crowd-redis")
public interface RedisRemoteService {

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
    );

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
            @RequestParam("timeUnit")TimeUnit timeUnit
            );

    /**
     * 根据 key 获取 value
     * @param key
     * @return
     */
    @GetMapping("/get/redis/string/value/by/key/remote")
    ResultEntity<String> getRedisStringValueByKeyRemote(@RequestParam("key") String key);

    /**
     * 根据 key 删除 redis 上的数据
     * @param key
     * @return
     */
    @DeleteMapping("/remove/redis/key/remote")
    ResultEntity<String> removeRedisKeyRemote(@RequestParam("key") String key);
}
