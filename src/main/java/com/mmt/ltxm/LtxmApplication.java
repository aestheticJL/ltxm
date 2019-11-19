package com.mmt.ltxm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.mmt.ltxm.mapper")
@SpringBootApplication
@EnableScheduling
public class LtxmApplication {

    public static void main(String[] args) {
        SpringApplication.run(LtxmApplication.class, args);
    }

}
