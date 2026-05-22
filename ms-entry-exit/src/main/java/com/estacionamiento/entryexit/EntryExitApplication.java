package com.estacionamiento.entryexit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EntryExitApplication {
    public static void main(String[] args) {
        SpringApplication.run(EntryExitApplication.class, args);
    }
}
