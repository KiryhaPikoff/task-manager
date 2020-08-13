package com.mediasoft.tmclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TmClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmClientApplication.class, args);
    }
}
