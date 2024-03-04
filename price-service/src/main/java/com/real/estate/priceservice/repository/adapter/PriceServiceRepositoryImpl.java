package com.real.estate.priceservice.repository.adapter;

import com.real.estate.priceservice.domain.dto.average.AveragePriceQuery;
import com.real.estate.priceservice.domain.entity.Estate;
import com.real.estate.priceservice.domain.entity.Region;
import com.real.estate.priceservice.domain.port.output.PriceServiceRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class PriceServiceRepositoryImpl implements PriceServiceRepository {
    @Override
    public Mono<Estate> save(Estate estate) {
        return null;
    }

    @Override
    public Flux<Estate> findByQuery(AveragePriceQuery averagePriceQuery, Integer value) {
        return null;
    }

    @Override
    public Flux<Region> findAllRegions() {
        return null;
    }

    @Override
    public Mono<Region> findByCode(String code) {
        return null;
    }
}
