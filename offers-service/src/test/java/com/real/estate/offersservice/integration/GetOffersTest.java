package com.real.estate.offersservice.integration;

import com.real.estate.offersservice.dto.CreateEstateOffersResponse;
import com.real.estate.offersservice.dto.RegionEstateOffers;
import com.real.estate.offersservice.entity.EstateOffer;
import com.real.estate.offersservice.entity.EstateType;
import com.real.estate.offersservice.entity.RegionCode;
import com.real.estate.offersservice.repository.EstateOffersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetOffersTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EstateOffersRepository estateOffersRepository;

    @BeforeEach
    void setUp() {
        var estateOffers = List.of(EstateOffer.builder()
                        .id(UUID.randomUUID())
                        .rooms(4)
                        .type(EstateType.FLAT)
                        .area(69.66f)
                        .price(new BigDecimal("666999.69"))
                        .regionCode(RegionCode.DLN_WROC_C)
                        .description("some description")
                        .createdDate(LocalDate.now())
                        .build(),
                EstateOffer.builder()
                        .id(UUID.randomUUID())
                        .rooms(3)
                        .type(EstateType.DETACHED_HOUSE)
                        .area(96.99f)
                        .price(new BigDecimal("999666.69"))
                        .regionCode(RegionCode.DLN_WROC_C)
                        .description("some description")
                        .createdDate(LocalDate.now())
                        .build(),
                EstateOffer.builder()
                        .id(UUID.randomUUID())
                        .rooms(2)
                        .type(EstateType.SEMI_DETACHED_HOUSE)
                        .area(66.69f)
                        .price(new BigDecimal("696969.69"))
                        .regionCode(RegionCode.DLN_WROC_C)
                        .description("some description")
                        .createdDate(LocalDate.now())
                        .build());

        estateOffersRepository
                .deleteAll()
                .thenMany(estateOffersRepository.saveAll(estateOffers))
                .blockLast();
    }

    @Test
    void shouldGetEstateOffers() {
        //Given & When
        var realEstateOffers =  webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/real-estates/{regionCode}")
                        .queryParam("page", "0")
                        .queryParam("size", "2")
                        .build("DLN_WROC_C"))
                .exchange()
                .expectStatus()
                .isEqualTo(OK)
                .expectHeader()
                .contentType(APPLICATION_JSON)
                .expectBody(RegionEstateOffers.class)
                .returnResult()
                .getResponseBody();

        //Then
        assertNotNull(realEstateOffers);
        assertEquals("2", realEstateOffers.getTotalPages());
        assertEquals(2, realEstateOffers.getData().size());
    }

}
