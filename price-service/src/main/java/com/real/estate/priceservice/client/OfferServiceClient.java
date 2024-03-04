package com.real.estate.priceservice.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
class OfferServiceClient {

    private final WebClient webClient;


    OfferServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }


}
