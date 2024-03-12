package com.real.estate.priceservice.repository.adapter;

import com.real.estate.priceservice.domain.dto.average.AveragePriceQuery;
import com.real.estate.priceservice.domain.entity.Estate;
import com.real.estate.priceservice.domain.entity.Region;
import com.real.estate.priceservice.domain.exception.EstateDomainException;
import com.real.estate.priceservice.domain.exception.FindEstateException;
import com.real.estate.priceservice.domain.exception.RegionFetchException;
import com.real.estate.priceservice.domain.port.output.PriceServiceRepository;
import com.real.estate.priceservice.repository.EstateJpaRepository;
import com.real.estate.priceservice.repository.RegionJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
class PriceServiceRepositoryImpl implements PriceServiceRepository {

    private final EstateJpaRepository estateJpaRepository;

    private final RegionJpaRepository regionJpaRepository;

//    private final DSLContext dslContext;

    PriceServiceRepositoryImpl(
            EstateJpaRepository estateJpaRepository, RegionJpaRepository regionJpaRepository
//                               DSLContext dslContext
    ) {
        this.estateJpaRepository = estateJpaRepository;
        this.regionJpaRepository = regionJpaRepository;
//        this.dslContext = dslContext;
    }

    @Override
    public Mono<Estate> save(Estate estate) {
//        return Mono.just(estate);
        return estateJpaRepository.save(estate)
                .switchIfEmpty(Mono.error(new EstateDomainException("Could not save estate with id " + estate.getId())))
                .doOnError(e -> log.error(e.getMessage()));
    }

    @Override
    public Flux<Estate> getAveragePriceByQuery(AveragePriceQuery averagePriceQuery, Pageable pageable) {
//        return Flux.fromIterable(List.of(Estate.builder().build()));
        Flux<Estate> average = estateJpaRepository.findAverage(
                averagePriceQuery.getRegionCode(),
                averagePriceQuery.getRooms(),
                averagePriceQuery.getRoomSize().getSinceSize(),
                averagePriceQuery.getRoomSize().getUntilSize(),
                averagePriceQuery.getTypes(),
                averagePriceQuery.getDataSince(),
                averagePriceQuery.getDataUntil()
        );
        return average.switchIfEmpty(Mono.error(new FindEstateException("Could not find averagePrice by providing parameters " + averagePriceQuery)))
//                .doOnSuccess(e -> log.info("Find averagePrice {} by providing parameters {}", e, averagePriceQuery))
                .doOnError(e -> log.error(e.getMessage()));
    }

    @Override
    public Flux<Region> findAllRegions() {
//        return Flux.fromIterable(List.of(Region.builder().build()));
        return regionJpaRepository.findAll()
                .switchIfEmpty(Flux.error(new RegionFetchException("Could not find regions")))
                .doOnError(e -> log.error(e.getMessage()));
    }

    @Override
    public Mono<Region> findRegionByCode(String code) {
//        return Mono.just(Region.builder().build());
        return regionJpaRepository.findByRegionCode(code)
                .switchIfEmpty(Mono.error(new RegionFetchException("Could not find region by code " + code)))
                .doOnError(e -> log.error(e.getMessage()));
    }
}
