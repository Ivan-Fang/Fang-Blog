package com.ivanfang.fangtimes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void create() {
        stringRedisTemplate.opsForValue().set("Linda", "732109urwfh");
    }

    @Test
    public void delete() {
        stringRedisTemplate.delete("Linda");
    }

}
