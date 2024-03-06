package com.real.estate.priceservice.client;

import com.real.estate.priceservice.client.mapper.PaymentEstateDataMapping;
import com.real.estate.priceservice.client.model.RealEstateData;
import com.real.estate.priceservice.client.model.RealEstatePayload;
import com.real.estate.priceservice.domain.dto.create.CreateCommand;
import com.real.estate.priceservice.domain.dto.create.CreateEstateResponse;
import com.real.estate.priceservice.domain.entity.Region;
import com.real.estate.priceservice.domain.port.input.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
class OfferServiceClientScheduler {

    private final OfferServiceClient offerServiceClient;

    private final ClientService clientService;

    private final PaymentEstateDataMapping paymentEstateDataMapping;

    OfferServiceClientScheduler(OfferServiceClient offerServiceClient, ClientService clientService, PaymentEstateDataMapping paymentEstateDataMapping) {
        this.offerServiceClient = offerServiceClient;
        this.clientService = clientService;
        this.paymentEstateDataMapping = paymentEstateDataMapping;
    }

    @Scheduled(
            cron = "${external-api.cron}"
//            ,initialDelayString = "1000"
    )
    public void fetchEstatesFromExternalApi() {
        Flux<Region> regions = clientService.fetchAllRegions();
        regions.flatMap(region -> getFirstPage(region)
                        .map(firstPage -> Flux.range(1, Integer.parseInt(firstPage.getTotalPages()))
                                .map(String::valueOf)
                                .flatMap(page -> createEstates(region, page))))
                .subscribe();
    }

    private Mono<CreateEstateResponse> createEstates(Region region, String page) {
        Mono<RealEstatePayload> payload = offerServiceClient
                .getRegionPageEstates(region
                        .getRegionCode(), page);
        Flux<CreateCommand> createCommands = payload.flatMapMany(p -> Flux.fromIterable(p.getData()))
                .map(realEstateData -> paymentEstateDataMapping.estatePayloadDataToUpsertCommand(realEstateData, region));
        return clientService.createEstates(createCommands);
    }

    private Mono<RealEstatePayload> getFirstPage(Region region) {
        return offerServiceClient
                .getRegionPageEstates(region
                        .getRegionCode(), "1");
    }

}
