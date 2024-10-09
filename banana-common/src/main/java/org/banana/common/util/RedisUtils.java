package org.banana.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate redisTemplate;

    public void set(String key, String value){
        redisTemplate.opsForValue().set(key,value);
    }
    public String get(String key){
        return (String) redisTemplate.opsForValue().get(key);
    }
}
