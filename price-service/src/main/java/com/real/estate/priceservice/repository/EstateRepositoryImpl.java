package com.real.estate.priceservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Repository
@Transactional(readOnly = true)
public class EstateRepositoryImpl implements EstateRepository{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Mono<Double> averagePrice() {
        return null;
    }
}
