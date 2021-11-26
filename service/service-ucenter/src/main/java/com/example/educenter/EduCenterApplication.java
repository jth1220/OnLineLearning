package com.example.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example"})
@MapperScan("com.example.educenter.mapper")
public class EduCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduCenterApplication.class,args);
    }
}
