package com.ppproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//TODO:: AWS S3 -> for images
@SpringBootApplication
@EnableSwagger2
public class PpProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PpProjectApplication.class, args);
    }

}
