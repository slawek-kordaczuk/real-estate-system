package com.real.estate.priceservice.client;

import com.real.estate.priceservice.domain.port.input.ClientService;
import org.springframework.stereotype.Component;

@Component
class OfferServiceClientScheduler {

    private final OfferServiceClient offerServiceClient;

    private final ClientService clientService;

    OfferServiceClientScheduler(OfferServiceClient offerServiceClient, ClientService clientService) {
        this.offerServiceClient = offerServiceClient;
        this.clientService = clientService;
    }



}
