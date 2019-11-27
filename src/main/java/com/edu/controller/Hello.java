package com.edu.controller;

import com.edu.common.RedisPoll;
import com.edu.untils.RedisApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {
    @Autowired
    RedisPoll redisPoll;
    @Autowired
    RedisApi redisApi;
    @RequestMapping("/testjedis")
    public String testJedis(){
        redisApi.set("java", "jsp");
        return redisApi.get("java");


    }


}
