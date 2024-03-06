package com.real.estate.priceservice.repository;

import reactor.core.publisher.Mono;

public interface EstateRepository {

    Mono<Double> averagePrice();
}
