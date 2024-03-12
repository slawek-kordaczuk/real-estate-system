package com.real.estate.offersservice.integration;

import com.real.estate.offersservice.dto.CreateEstateOffersResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreateOffersTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldCreateEstateOffers() {
        //Given & When
        var numberOfCreatedOffers =  webTestClient.put()
                .uri("/api/create")
                .exchange()
                .expectStatus()
                .isEqualTo(OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(CreateEstateOffersResponse.class)
                .returnResult()
                .getResponseBody()
                .getNumberOfCreatedOffers();

        //Then
        assertNotNull(numberOfCreatedOffers);
        assertTrue("number of created offers are between 1040 and 1170",
                1040 <= Integer.parseInt(numberOfCreatedOffers)
                && Integer.parseInt(numberOfCreatedOffers)  <= 1170);
    }
}
