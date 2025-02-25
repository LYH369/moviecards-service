package com.lauracercas.moviecards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
@SpringBootApplication
public class MovieCardsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieCardsApplication.class, args);
    }
    
    // 新增的 Bean，用于在 Service 层调用外部 REST API
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
