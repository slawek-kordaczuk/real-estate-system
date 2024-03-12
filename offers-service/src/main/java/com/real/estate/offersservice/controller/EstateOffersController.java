package com.real.estate.offersservice.controller;

import com.real.estate.offersservice.dto.CreateEstateOffersResponse;
import com.real.estate.offersservice.dto.RegionEstateOffers;
import com.real.estate.offersservice.entity.EstateOffer;
import com.real.estate.offersservice.service.EstateOffersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

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

    @GetMapping("/real-estates/{regionCode}")
    public Mono<ResponseEntity<RegionEstateOffers>> getAllTutorialsPage(
            @PathVariable String regionCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page, size);
        var regionEstateOffers = estateOffersService.regionEstateOffers(regionCode, paging);
        return regionEstateOffers.map(offers -> ResponseEntity.ok()
                .body(offers));
    }
}
