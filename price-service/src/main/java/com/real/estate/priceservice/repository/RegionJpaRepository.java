package com.real.estate.priceservice.repository;

import com.real.estate.priceservice.domain.entity.Region;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface RegionJpaRepository extends R2dbcRepository<Region, UUID> {

    Mono<Region> findByRegionCode(String regionCode);
}
