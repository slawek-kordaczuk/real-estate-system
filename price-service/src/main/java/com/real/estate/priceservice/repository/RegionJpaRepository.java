package com.real.estate.priceservice.repository;

import com.real.estate.priceservice.domain.entity.Estate;
import com.real.estate.priceservice.domain.entity.Region;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface RegionJpaRepository extends ReactiveCrudRepository<Region, UUID> {
    Mono<Region> findByRegionCode(String regionCode);
}
