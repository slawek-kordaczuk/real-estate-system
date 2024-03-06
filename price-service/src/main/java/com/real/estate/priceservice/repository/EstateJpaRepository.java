package com.real.estate.priceservice.repository;

import com.real.estate.priceservice.domain.dto.average.AveragePriceProjection;
import com.real.estate.priceservice.domain.dto.average.AveragePriceResponse;
import com.real.estate.priceservice.domain.entity.Estate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface EstateJpaRepository extends R2dbcRepository<Estate, Integer> {
    @Query(
//            "SELECT estate " +
            "SELECT AVG(estate.price) " +
                    "FROM Estate estate " +
                    "WHERE estate.regionCode = :regionCode " +
                    "AND estate.type IN :types " +
                    "AND estate.area >= :sizeSince " +
                    "AND estate.area <= :sizeUntil " +
                    "AND estate.createdDate >= :dateSince " +
                    "AND estate.createdDate <= :dateUntil " +
                    "AND estate.rooms = :rooms")
    Mono<BigDecimal> findBy(@Param("regionCode") String regionCode,
                            @Param("rooms") Integer rooms,
                            @Param("sizeSince") Integer sizeSince,
                            @Param("sizeUntil") Integer sizeUntil,
                            @Param("types") List<String> types,
                            @Param("dateSince") LocalDate fromDate,
                            @Param("dateUntil") LocalDate toDate);
}
