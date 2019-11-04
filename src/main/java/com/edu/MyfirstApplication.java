package com.edu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//MapperScan:配置dao接口生成实现类，并且放到容器里
@MapperScan("com.edu.dao")
//定时任务注解
@EnableScheduling
public class MyfirstApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyfirstApplication.class, args);
    }

}
