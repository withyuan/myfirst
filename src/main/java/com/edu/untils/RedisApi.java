package com.edu.untils;

import com.edu.common.RedisPoll;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

public class RedisApi {
    @Autowired
    RedisPoll redisPoll;
/**
 * 字符串
 */
    public  String set(String key,String value){

        Jedis jedis = redisPoll.getJedis();
        String result=null;
        try{
            result= jedis.set(key,value);
            redisPoll.returnJedis(jedis);
        }catch (Exception e){
            redisPoll.returnBrokenResource(jedis);
        }
        return result;

    }
    public  String get(String key){

        Jedis jedis = redisPoll.getJedis();
        String result=null;
        try{
            result= jedis.get(key);
            redisPoll.returnJedis(jedis);
        }catch (Exception e){
            redisPoll.returnBrokenResource(jedis);
        }
        return result;
    }
    public  String setex(String key,int timeout,String value){

        Jedis jedis = redisPoll.getJedis();
        String result=null;
        try{
            result= jedis.setex(key,timeout,value);
            redisPoll.returnJedis(jedis);
        }catch (Exception e){
            redisPoll.returnBrokenResource(jedis);
        }
        return result;
    }
    public  Long expire(String key,int timeout){

        Jedis jedis = redisPoll.getJedis();
        Long result=null;
        try{
            result= jedis.expire(key,timeout);
            redisPoll.returnJedis(jedis);
        }catch (Exception e){
            redisPoll.returnBrokenResource(jedis);
        }
        return result;
    }


}
