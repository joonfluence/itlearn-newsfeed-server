package com.newsfeed.itlearn.domain.test;

import com.newsfeed.itlearn.global.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class TestController {

    @Autowired
    private RedisService redisService;

    @PostMapping("/set/{key}")
    public String setValueInRedis(@PathVariable String key, @RequestBody String value) {
        redisService.saveValue(key, value);
        return "Value set successfully!";
    }

    @GetMapping("/get/{key}")
    public Object getValueInRedis(@PathVariable String key) {
        Object value = redisService.getValue(key);
        return value != null ? value : "Key not found";
    }
}
