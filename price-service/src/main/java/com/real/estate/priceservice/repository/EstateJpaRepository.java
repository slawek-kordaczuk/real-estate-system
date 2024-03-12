package com.real.estate.priceservice.repository;

import com.real.estate.priceservice.domain.entity.Estate;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface EstateJpaRepository extends ReactiveCrudRepository<Estate, UUID> {
    @Query(
            "SELECT estate " +
//            "SELECT AVG(estate.price) " +
                    "FROM Estate estate " +
                    "WHERE estate.regionCode = :regionCode " +
                    "AND estate.type IN :types " +
                    "AND estate.area >= :sizeSince " +
                    "AND estate.area <= :sizeUntil " +
                    "AND estate.createdDate >= :dateSince " +
                    "AND estate.createdDate <= :dateUntil " +
                    "AND estate.rooms = :rooms")
    Flux<Estate> findAverage(@Param("regionCode") String regionCode,
                        @Param("rooms") Integer rooms,
                        @Param("sizeSince") Integer sizeSince,
                        @Param("sizeUntil") Integer sizeUntil,
                        @Param("types") List<String> types,
                        @Param("dateSince") LocalDate fromDate,
                        @Param("dateUntil") LocalDate toDate);
}
