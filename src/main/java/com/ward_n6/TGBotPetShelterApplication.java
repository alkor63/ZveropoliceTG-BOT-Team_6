package com.ward_n6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"java"})
@EnableScheduling // для таймера

public class TGBotPetShelterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TGBotPetShelterApplication.class, args);
    }

}
