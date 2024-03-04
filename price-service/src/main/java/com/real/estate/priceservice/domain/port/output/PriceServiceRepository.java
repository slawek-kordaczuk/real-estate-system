package com.real.estate.priceservice.domain.port.output;

import com.real.estate.priceservice.domain.dto.average.AveragePriceQuery;
import com.real.estate.priceservice.domain.entity.Estate;
import com.real.estate.priceservice.domain.entity.Region;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PriceServiceRepository {

    Mono<Estate> save(Estate estate);

    Flux<Estate> findByQuery(AveragePriceQuery averagePriceQuery, Integer value);

    Flux<Region> findAllRegions();
    Mono<Region> findByCode(String code);
}
