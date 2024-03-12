package com.real.estate.priceservice.domain.port.output;

import com.real.estate.priceservice.domain.dto.average.AveragePriceQuery;
import com.real.estate.priceservice.domain.entity.Estate;
import com.real.estate.priceservice.domain.entity.Region;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

public interface PriceServiceRepository {

    Mono<Estate> save(Estate estate);

    Flux<Estate> getAveragePriceByQuery(AveragePriceQuery averagePriceQuery, Pageable pageable);

    Flux<Region> findAllRegions();

    Mono<Region> findRegionByCode(String code);
}
