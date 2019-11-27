package com.edu.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis的连接池
 */
public class RedisPoll {
    private JedisPool jedisPool;
   public RedisPoll(){

   }
   public RedisPoll(Integer maxTotal,
                    Integer maxIdle,
                    Integer minIdle,
                    Integer port,
                    boolean testborrow,
                    boolean testreturn,
                    Integer timeout,
                    String password,String ip
   ) {
       JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
       jedisPoolConfig.setMaxTotal(maxTotal);//最大连接数
       jedisPoolConfig.setMaxIdle(maxIdle);//最大空闲数
       jedisPoolConfig.setMinIdle(minIdle);//最小空闲数
       jedisPoolConfig.setTestOnBorrow(testborrow);//true：当从连接池获取链接时，检测链接是否有效
       jedisPoolConfig.setTestOnReturn(testreturn);//true：当链接放回到连接池上，检测是否有效
       jedisPoolConfig.setBlockWhenExhausted(true);//true：当连接池的链接耗尽时，再有链接过来会等待直到超时
       jedisPool=new JedisPool(jedisPoolConfig,ip,port,timeout,password);


   }
   public Jedis getJedis(){
       return  jedisPool.getResource();

   }
        public void returnJedis(Jedis j){
       jedisPool.returnResource(j);

        }
    public void returnBrokenResource(Jedis j){
        jedisPool.returnBrokenResource(j);

    }

}
