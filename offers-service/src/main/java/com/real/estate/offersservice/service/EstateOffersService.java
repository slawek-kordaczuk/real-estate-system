package com.real.estate.offersservice.service;

import com.real.estate.offersservice.dto.CreateEstateOffersResponse;
import com.real.estate.offersservice.dto.RegionEstateOffers;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface EstateOffersService {

    Mono<CreateEstateOffersResponse> createEstateOffers();

    Mono<RegionEstateOffers> regionEstateOffers(Pageable pageable);
}
