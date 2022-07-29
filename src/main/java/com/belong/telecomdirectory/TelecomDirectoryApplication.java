package com.belong.telecomdirectory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EntityScan(basePackages = { "com.belong.*" })
public class TelecomDirectoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelecomDirectoryApplication.class, args);
    }

}
