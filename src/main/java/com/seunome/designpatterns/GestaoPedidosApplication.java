package com.seunome.designpatterns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class GestaoPedidosApplication {
    public static void main(String[] args) {
        SpringApplication.run(GestaoPedidosApplication.class, args);
    }
}
