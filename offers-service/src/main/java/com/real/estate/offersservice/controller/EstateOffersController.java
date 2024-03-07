package com.real.estate.offersservice.controller;

import com.real.estate.offersservice.dto.CreateEstateOffersResponse;
import com.real.estate.offersservice.entity.EstateOffer;
import com.real.estate.offersservice.service.EstateOffersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@Slf4j
public class EstateOffersController {

    private final EstateOffersService estateOffersService;

    public EstateOffersController(EstateOffersService estateOffersService) {
        this.estateOffersService = estateOffersService;
    }

    @PutMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<CreateEstateOffersResponse>> addEstateOffers() {
        var addEstate = estateOffersService.createEstateOffers();
        return addEstate.map(addedEstate -> ResponseEntity.ok()
                .body(addedEstate));
    }
}
