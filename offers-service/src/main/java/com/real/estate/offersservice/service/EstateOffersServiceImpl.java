package com.real.estate.offersservice.service;

import com.real.estate.offersservice.dto.CreateEstateOffersResponse;
import com.real.estate.offersservice.dto.EstateData;
import com.real.estate.offersservice.dto.RegionEstateOffers;
import com.real.estate.offersservice.entity.EstateOffer;
import com.real.estate.offersservice.entity.RegionCode;
import com.real.estate.offersservice.repository.EstateOffersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
                .flatMap(regionCode ->
                        estateOffersRepository.saveAll(
                                estateOffersGenerator.createEstateOffers(regionCode)
                        )
                )
                .collectList()
                .map(estateOffers -> new CreateEstateOffersResponse(String.valueOf(estateOffers.size())));
    }

    @Override
    public Mono<RegionEstateOffers> regionEstateOffers(String regionCode, Pageable pageable) {
        return findAllOffersByRegion(regionCode, pageable)
                .map(estateOffers -> new RegionEstateOffers(String.valueOf(estateOffers.getTotalPages()),
                        mapToEstateData(estateOffers)));
    }

    private List<EstateData> mapToEstateData(Page<EstateOffer> estateOffers) {
        return estateOffers.stream().map(estateOffer -> EstateData.builder()
                .id(String.valueOf(estateOffer.getId()))
                .rooms(String.valueOf(estateOffer.getRooms()))
                .area(String.valueOf(estateOffer.getArea()))
                .price(String.valueOf(estateOffer.getPrice()))
                .type(String.valueOf(estateOffer.getType()))
                .description(String.valueOf(estateOffer.getDescription()))
                .build()).toList();
    }

    private Mono<Page<EstateOffer>> findAllOffersByRegion(String regionCode, Pageable pageable) {
        return this.estateOffersRepository.findAllByRegionCode(regionCode, pageable)
                .collectList()
                .zipWith(this.estateOffersRepository.count())
                .map(p -> new PageImpl<>(p.getT1(), pageable, p.getT2()));
    }
}
