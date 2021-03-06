package com.example.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.example"})
@MapperScan("com.example.educenter.mapper")
@EnableFeignClients
public class EduCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduCenterApplication.class,args);
    }
}
