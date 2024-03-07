package com.real.estate.offersservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class OfferServiceConfig {

    @Bean
    public Random random(){
        return new Random();
    }
}
