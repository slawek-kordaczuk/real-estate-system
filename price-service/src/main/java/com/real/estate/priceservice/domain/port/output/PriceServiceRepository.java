package com.real.estate.priceservice.domain.port.output;

import com.real.estate.priceservice.domain.dto.average.AveragePriceQuery;
import com.real.estate.priceservice.domain.entity.Estate;
import com.real.estate.priceservice.domain.entity.Region;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface PriceServiceRepository {

    Mono<Estate> save(Estate estate);

    Mono<BigDecimal> getAveragePriceByQuery(AveragePriceQuery averagePriceQuery);

    Flux<Region> findAllRegions();

    Mono<Region> findRegionByCode(String code);
}
