package com.real.estate.priceservice.client;

import com.real.estate.priceservice.client.mapper.PaymentEstateDataMapping;
import com.real.estate.priceservice.client.dto.RealEstateData;
import com.real.estate.priceservice.client.dto.RealEstatePayload;
import com.real.estate.priceservice.domain.dto.create.CreateEstateResponse;
import com.real.estate.priceservice.domain.entity.Region;
import com.real.estate.priceservice.domain.port.input.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OfferServiceClientSchedulerTest {

    private OfferServiceClient priceServiceClient;

    private ClientService clientService;

    private OfferServiceClientScheduler offerServiceClientScheduler;

    @BeforeEach
    void setUp() {
        this.priceServiceClient = Mockito.mock(OfferServiceClient.class);
        this.clientService = Mockito.mock(ClientService.class);
        PaymentEstateDataMapping paymentEstateDataMapping = new PaymentEstateDataMapping();
        offerServiceClientScheduler = new OfferServiceClientScheduler(priceServiceClient, clientService, paymentEstateDataMapping);
    }


    @Test
    void fetchEstatesFromExternalApi() {
        // given
        Region region1 = Region.builder()
                .id(1)
                .regionCode("TEST_REGION_1")
                .description("test description")
                .build();
        Region region2 = Region.builder()
                .id(1)
                .regionCode("TEST_REGION_2")
                .description("test description")
                .build();
        RealEstatePayload payload = RealEstatePayload.builder()
                .totalPages("2")
                .data(List.of(RealEstateData.builder()
                        .id("052a0d32-b364-4430-afcc-91d74c3545bb")
                        .type("detached_house")
                        .area("120")
                        .rooms("4")
                        .price("760332.99")
                        .description("A really nice house you should buy it")
                        .build()))
                .build();
        when(clientService.fetchAllRegions()).thenReturn(Flux.fromIterable(List.of(region1, region2)));
        when(clientService.createEstates(any())).thenReturn(Mono.just(CreateEstateResponse.builder()
                .numberOfCreatedEstates(5)
                .build()));
        when(priceServiceClient.getRegionPageEstates(any(String.class), any(String.class))).thenReturn(Mono.just(payload));

        // when
        offerServiceClientScheduler.fetchEstatesFromOfferService();

        // then
        verify(clientService, times(1)).fetchAllRegions();
        verify(priceServiceClient, times(2)).getRegionPageEstates(region1.getRegionCode(), "1");
        verify(priceServiceClient, times(1)).getRegionPageEstates(region1.getRegionCode(), "2");
        verify(priceServiceClient, times(2)).getRegionPageEstates(region2.getRegionCode(), "1");
        verify(priceServiceClient, times(1)).getRegionPageEstates(region2.getRegionCode(), "2");
        verify(clientService, times(6)).createEstates(any());
    }
}
