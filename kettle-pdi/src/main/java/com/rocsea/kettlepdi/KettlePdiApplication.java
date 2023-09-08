package com.rocsea.kettlepdi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.rocsea.kettlepdi", "com.rocsea.etlcommon"})
public class KettlePdiApplication {

    public static void main(String[] args) {
        SpringApplication.run(KettlePdiApplication.class, args);
    }

}
