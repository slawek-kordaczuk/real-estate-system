package com.real.estate.offersservice;

import com.real.estate.offersservice.dto.CreateEstateOffersResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OffersServiceApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldCreateEstateOffers() {
        //Given & When
        CreateEstateOffersResponse response = webTestClient.put()
                .uri("/api/create")
                .exchange()
                .expectStatus()
                .isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(CreateEstateOffersResponse.class)
                .returnResult()
                .getResponseBody();

        //Then
        assertEquals("0", response.getNumberOfCreatedOffers());
    }
}
