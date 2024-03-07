package com.real.estate.priceservice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.real.estate.priceservice.client.dto.RealEstatePayload;
import com.real.estate.priceservice.domain.exception.ExternalApiClientException;
import com.real.estate.priceservice.domain.exception.ExternalApiServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Slf4j
@Component
class OfferServiceClient {

    private final WebClient webClient;

    private final ObjectMapper objectMapper;

    @Value("${external-api.number-of-retries}")
    private int numberOfRetries;


    OfferServiceClient(WebClient webClient, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }

    public Mono<RealEstatePayload> getRegionPageEstates(String regionCode, String page) {
        Mono<String> payload =
                webClient
                        .get()
                        .uri("/api/real-estates/" + regionCode + "?page=" + page)
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                            log.info("Status code : {}", clientResponse.statusCode().value());
                            return Mono.error(new ExternalApiClientException("There is no Estate available for the passed in region code : " + regionCode));
                        })
                        .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                            log.info("Status code : {}", clientResponse.statusCode().value());
                            return clientResponse.bodyToMono(String.class)
                                    .flatMap(response -> Mono.error(new ExternalApiServerException(response)));
                        })
                        .bodyToMono(String.class)
                        .retryWhen(retrySpec());

        return convertToRealEstatePayload(payload);
    }

    private Mono<RealEstatePayload> convertToRealEstatePayload(Mono<String> realEstateResponse) {
        return realEstateResponse.flatMap(this::readValueFromPayload);
    }

    private Mono<RealEstatePayload> readValueFromPayload(String response) {
        try {
            return Mono.just(objectMapper.readValue(response, RealEstatePayload.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private Retry retrySpec() {
        return Retry.fixedDelay(numberOfRetries, Duration.ofSeconds(1))
                .filter(ExternalApiServerException.class::isInstance)
                .onRetryExhaustedThrow(((retryBackoffSpec, retrySignal) -> Exceptions.propagate(retrySignal.failure())));

    }

}
