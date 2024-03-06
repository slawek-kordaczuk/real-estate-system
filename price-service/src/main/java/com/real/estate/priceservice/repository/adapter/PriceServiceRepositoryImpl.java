package com.real.estate.priceservice.repository.adapter;

import com.real.estate.priceservice.domain.dto.average.AveragePriceQuery;
import com.real.estate.priceservice.domain.entity.Estate;
import com.real.estate.priceservice.domain.entity.Region;
import com.real.estate.priceservice.domain.exception.FindEstateException;
import com.real.estate.priceservice.domain.exception.RegionFetchException;
import com.real.estate.priceservice.domain.port.output.PriceServiceRepository;
import com.real.estate.priceservice.repository.EstateJpaRepository;
import com.real.estate.priceservice.repository.RegionJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Slf4j
@Repository
class PriceServiceRepositoryImpl implements PriceServiceRepository {

    private final EstateJpaRepository estateJpaRepository;

    private final RegionJpaRepository regionJpaRepository;

    PriceServiceRepositoryImpl(EstateJpaRepository estateJpaRepository, RegionJpaRepository regionJpaRepository) {
        this.estateJpaRepository = estateJpaRepository;
        this.regionJpaRepository = regionJpaRepository;
    }

    @Override
    public Mono<Estate> save(Estate estate) {
        return estateJpaRepository.save(estate);
    }

    @Override
    public Mono<BigDecimal> getAveragePriceByQuery(AveragePriceQuery averagePriceQuery) {
        Mono<BigDecimal> average = estateJpaRepository.findBy(averagePriceQuery.getRegionCode(),
                averagePriceQuery.getRooms(),
                averagePriceQuery.getRoomSize().getSinceSize(),
                averagePriceQuery.getRoomSize().getUntilSize(),
                averagePriceQuery.getTypes(),
                averagePriceQuery.getDataSince(),
                averagePriceQuery.getDataUntil());
        return average.switchIfEmpty(Mono.error(new FindEstateException("Could not find averagePrice by providing parameters " + averagePriceQuery)))
                .doOnSuccess(e -> log.info("Find averagePrice {} by providing parameters {}", e, averagePriceQuery))
                .doOnError(e -> log.error("Could not find averagePrice by providing parameters {}", averagePriceQuery));
    }

    @Override
    public Flux<Region> findAllRegions() {
        return regionJpaRepository.findAll()
                .switchIfEmpty(Flux.error(new RegionFetchException("Could not find regions")))
                .doOnError(e -> log.error("Could not find regions"));
    }

    @Override
    public Mono<Region> findRegionByCode(String code) {
        return regionJpaRepository.findByRegionCode(code)
                .switchIfEmpty(Mono.error(new RegionFetchException("Could not find region by code " + code)))
                .doOnError(e -> log.error("Could not find region by code {}", code));
    }
}
