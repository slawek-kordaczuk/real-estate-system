package com.real.estate.orderservice.dataaccess.repository;

import com.real.estate.orderservice.dataaccess.entity.EstateEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface EstateJpaRepository extends R2dbcRepository<EstateEntity, UUID> {
    @Query("SELECT estate FROM EstateEntity estate " +
            "WHERE estate.region.id = :regionId " +
            "AND estate.type IN :types " +
            "AND estate.area >= :sizeSince " +
            "AND estate.area <= :sizeUntil " +
            "AND estate.createdDate >= :dateSince " +
            "AND estate.createdDate <= :dateUntil " +
            "AND estate.rooms = :rooms")
    Flux<EstateEntity> findBy(@Param("regionId") Integer regionId,
                                          @Param("rooms") Integer rooms,
                                          @Param("sizeSince") Integer sizeSince,
                                          @Param("sizeUntil") Integer sizeUntil,
                                          @Param("types") List<String> types,
                                          @Param("dateSince") LocalDate fromDate,
                                          @Param("dateUntil") LocalDate toDate);
}
