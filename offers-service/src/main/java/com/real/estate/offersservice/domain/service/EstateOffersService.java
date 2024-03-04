package com.real.estate.offersservice.domain.service;

import com.real.estate.offersservice.domain.entity.EstateOffer;
import com.real.estate.offersservice.repository.EstateOffersRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
public class EstateOffersService {

    private final EstateOffersRepository estateOffersRepository;

    public EstateOffersService(EstateOffersRepository estateOffersRepository) {
        this.estateOffersRepository = estateOffersRepository;
    }

    public Mono<EstateOffer> addEstateOffer(@Valid EstateOffer estateOffer){
        return estateOffersRepository.save(estateOffer);
    }

    public Mono<EstateOffer> addEstateOffer(String id, String description) {
        return estateOffersRepository.save(new EstateOffer(UUID.fromString(id), description));
    }

    public Mono<EstateOffer> getEstate(String id) {
        return estateOffersRepository.findById(UUID.fromString(id));
    }
}
