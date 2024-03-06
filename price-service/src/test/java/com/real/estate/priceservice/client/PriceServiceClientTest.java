package com.real.estate.priceservice.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.real.estate.priceservice.client.model.RealEstatePayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
@AutoConfigureWireMock(port = 8084)
@TestPropertySource(properties = {
        "external-api.offer-service-host=http://localhost:8084"
})
public class PriceServiceClientTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    private OfferServiceClient offerServiceClient;

    @BeforeEach
    void setUp() {
        WireMock.reset();
    }

    @Test
    void testExternalApiClient() throws Exception {
        // Given

        stubFor(get(urlEqualTo("/api/real-estates/TEST_REGION?page=1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("TEST_REGION.json")));

        // When
        var result = offerServiceClient.getRegionPageEstates("TEST_REGION", "1");

        // Then
        StepVerifier.create(result)
                .expectNext()
                .thenCancel()
                .verify();
    }

}
