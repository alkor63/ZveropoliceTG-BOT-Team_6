package com.ward_n6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.ward_n6")
@EnableScheduling // для таймера
public class TGBotPetShelterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TGBotPetShelterApplication.class, args);
    }

}
