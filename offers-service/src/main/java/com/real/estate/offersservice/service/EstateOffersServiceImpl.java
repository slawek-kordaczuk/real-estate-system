package com.real.estate.offersservice.service;

import com.real.estate.offersservice.dto.CreateEstateOffersResponse;
import com.real.estate.offersservice.dto.RegionEstateOffers;
import com.real.estate.offersservice.entity.RegionCode;
import com.real.estate.offersservice.repository.EstateOffersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
class EstateOffersServiceImpl implements EstateOffersService {

    private final EstateOffersRepository estateOffersRepository;

    private final EstateOffersGenerator estateOffersGenerator;

    public EstateOffersServiceImpl(EstateOffersRepository estateOffersRepository, EstateOffersGenerator estateOffersGenerator) {
        this.estateOffersRepository = estateOffersRepository;
        this.estateOffersGenerator = estateOffersGenerator;
    }

    @Override
    public Mono<CreateEstateOffersResponse> createEstateOffers() {
        return Flux.fromArray(RegionCode.values())
                        .flatMap(regionCode -> estateOffersRepository
                                .saveAll(estateOffersGenerator.createEstateOffers(regionCode.name())))
                .collectList()
                .map(estateOffers -> new CreateEstateOffersResponse(String.valueOf(estateOffers.size())));
    }

    @Override
    public Mono<RegionEstateOffers> regionEstateOffers(Pageable pageable) {
        return null;
    }
}
