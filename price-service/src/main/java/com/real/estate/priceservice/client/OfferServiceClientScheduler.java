package com.real.estate.priceservice.client;

import com.real.estate.priceservice.client.mapper.PaymentEstateDataMapping;
import com.real.estate.priceservice.client.dto.RealEstateData;
import com.real.estate.priceservice.client.dto.RealEstatePayload;
import com.real.estate.priceservice.domain.dto.create.CreateCommand;
import com.real.estate.priceservice.domain.dto.create.CreateEstateResponse;
import com.real.estate.priceservice.domain.entity.Region;
import com.real.estate.priceservice.domain.port.input.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

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
    public void fetchEstatesFromOfferService() {
        Flux<Region> regions = clientService.fetchAllRegions();
        regions.flatMap(region -> getNumberOfTotalPages(region)
                        .subscribeOn(Schedulers.boundedElastic())
                        .flatMapMany(numberOfPages -> Flux.range(1, Integer.parseInt(numberOfPages)))
                        .flatMap(page -> getPayload(region, String.valueOf(page))).log()
                        .flatMap(payload -> createEstates(payload.getData(), region)))
                .doOnError(error -> log.error(error.getMessage()))
                .subscribe();
    }

    private Mono<CreateEstateResponse> createEstates(List<RealEstateData> realEstateData, Region region) {
        List<CreateCommand> createCommands = realEstateData.stream()
                .map(estateData -> paymentEstateDataMapping.estatePayloadDataToUpsertCommand(estateData, region))
                .toList();
        return clientService.createEstates(createCommands);
    }

    private Mono<String> getNumberOfTotalPages(Region region) {
        return offerServiceClient
                .getRegionPageEstates(region
                        .getRegionCode(), "1")
                .map(RealEstatePayload::getTotalPages);
    }

    private Mono<RealEstatePayload> getPayload(Region region, String page) {
        return offerServiceClient
                .getRegionPageEstates(region
                        .getRegionCode(), page);
    }

}
