package com.real.estate.offersservice.controller;

import com.real.estate.offersservice.domain.entity.EstateOffer;
import com.real.estate.offersservice.domain.service.EstateOffersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/offers")
@Slf4j
public class EstateOffersController {

    private final EstateOffersService estateOffersService;

    public EstateOffersController(EstateOffersService estateOffersService) {
        this.estateOffersService = estateOffersService;
    }

    @PutMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<EstateOffer>> addMovieInfo(@RequestParam String id,
                                                          @RequestParam String description) {
        var addEstate = estateOffersService.addEstateOffer(id, description);
        return addEstate.map(addedEstate -> ResponseEntity.ok()
                .body(addedEstate));
    }

    @GetMapping("/get")
    public Mono<ResponseEntity<EstateOffer>> getEstate(@RequestParam String id) {
        var estate = estateOffersService.getEstate(id);
        return estate.map(addedEstate -> ResponseEntity.ok()
                .body(addedEstate));
    }
}
