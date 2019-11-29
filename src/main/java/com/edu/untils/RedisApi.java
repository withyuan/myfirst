package com.edu.untils;

import com.edu.common.RedisPoll;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

public class RedisApi {
    @Autowired
    RedisPoll jedisPool;

    /**
     * @param  key
     * @param  t
     * 添加key-value
     * */
    public   <T>   String  set(String key,T t){

        Jedis jedis=null;
        String result=null;
        try{
            jedis=jedisPool.getJedis();
            String jsonvalue=JsonUtils.obj2String(t);
            result=jedis.set(key, jsonvalue);
            return result;
        } catch (Exception e){
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        }finally {
            jedisPool.returnJedis(jedis);
        }

        return  null;
    }
    /**
     * get
     * */
    public <T> T get(String key,Class<T> clazz){

        Jedis jedis=null;
        try{
            jedis=  jedisPool.getJedis();
            String json=jedis.get(key);
            T t=JsonUtils.string2Obj(json,clazz);
            return t;
        }catch (Exception e){
            e.printStackTrace();
            if(jedis!=null){
                jedisPool.returnBrokenResource(jedis);
            }
        }finally {
            if(jedis!=null){
                jedisPool.returnJedis(jedis);
            }
        }
        return null;
    }
    /**
     * 设置过期时间的key-value
     * */
    public  <T>  String  setex(String key,T t,int expireTime){

        Jedis jedis=null;
        String result=null;
        try{
            jedis=jedisPool.getJedis();
            String jsonvalue=JsonUtils.obj2String(t);
            result=jedis.setex(key,expireTime, jsonvalue);
        } catch (Exception e){
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        }finally {
            jedisPool.returnJedis(jedis);
        }

        return  result;
    }
    /**
     * 删除
     * */
    public    Long  del(String key){

        Jedis jedis=null;
        Long result=null;
        try{
            jedis=jedisPool.getJedis();
            result=jedis.del(key);
        } catch (Exception e){
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        }finally {
            jedisPool.returnJedis(jedis);
        }

        return  result;
    }
    /**
     * 设置key的有效时间
     * */
    public    Long  expire(String key,int expireTime){

        Jedis jedis=null;
        Long result=null;
        try{
            jedis=jedisPool.getJedis();
            result=jedis.expire(key,expireTime);
        } catch (Exception e){
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        }finally {
            jedisPool.returnJedis(jedis);
        }

        return  result;
    }


}
