package com.real.estate.offersservice.repository;

import com.real.estate.offersservice.entity.EstateOffer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface EstateOffersRepository extends ReactiveMongoRepository<EstateOffer, UUID> {

    Flux<EstateOffer> findAllByRegionCode(String regionCode, Pageable pageable);
}
