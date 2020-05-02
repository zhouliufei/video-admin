package com.yefeng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yefeng.mapper")
public class VideoAdminWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoAdminWebApplication.class, args);
    }

}
