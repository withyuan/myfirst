package com.edu.config;

import com.edu.common.RedisPoll;
import com.edu.untils.RedisApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {
    @Value("${redis.max.total}")
    private Integer maxTotal;
    @Value("${redis.min.idle}")
    private Integer minIdle;
    @Value("${redis.max.idle}")
    private Integer maxIdle;
    @Value("${redis.port}")
    private Integer port;
    @Value("${redis.test.borrow}")
    private Boolean testborrow;
    @Value("${redis.test.return}")
    private Boolean testreturn;
    @Value("${redis.timeout}")
    private Integer timeout;
    @Value("${redis.password}")
    private String  password;
    @Value("${redis.ip}")
    private String ip;

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Boolean getTestborrow() {
        return testborrow;
    }

    public void setTestborrow(Boolean testborrow) {
        this.testborrow = testborrow;
    }

    public Boolean getTestreturn() {
        return testreturn;
    }

    public void setTestreturn(Boolean testreturn) {
        this.testreturn = testreturn;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    //Integer maxTotal,
//                    Integer maxIdle,
//                    Integer minIdle,
//                    Integer port,
//                    boolean testborrow,
//                    boolean testreturn,
//                    Integer timeout,
//                    String password,String ip
    @Bean
    public RedisPoll redisPoll(){

            return  new RedisPoll(maxTotal,
                    maxIdle,
                    minIdle,
                    port,testborrow,testreturn,timeout,password,
                    ip);
    }
    @Bean
    public RedisApi redisApi(){
        return new RedisApi();
    }

}
