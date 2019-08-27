package com.yzb.andong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.yzb.andong"})
@MapperScan("com.yzb.andong.dao")
public class AndongApplication {

    public static void main(String[] args) {
        SpringApplication.run(AndongApplication.class, args);
    }

}
