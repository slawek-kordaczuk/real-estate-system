package com.real.estate.offersservice.repository;

import com.real.estate.offersservice.domain.entity.EstateOffer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface EstateOffersRepository extends ReactiveMongoRepository<EstateOffer, UUID> {
}
