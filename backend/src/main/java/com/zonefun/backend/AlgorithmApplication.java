package com.zonefun.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName AlgorithmContext
 * @Description 启动类
 * @Date 2022/11/11 9:07
 * @Author ZoneFang
 */
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan({"com.zonefun.backend.mapper"})
@EnableTransactionManagement
public class AlgorithmApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlgorithmApplication.class, args);
    }
}
