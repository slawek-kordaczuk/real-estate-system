package com.real.estate.priceservice.service;

import com.real.estate.priceservice.domain.dto.create.CreateCommand;
import com.real.estate.priceservice.domain.dto.create.CreateEstateResponse;
import com.real.estate.priceservice.domain.entity.Estate;
import com.real.estate.priceservice.domain.entity.Region;
import com.real.estate.priceservice.domain.exception.EstateDomainException;
import com.real.estate.priceservice.domain.port.input.ClientService;
import com.real.estate.priceservice.domain.port.output.PriceServiceRepository;
import com.real.estate.priceservice.service.mapper.EstateDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
class ClientServiceImpl implements ClientService {

    private final PriceServiceRepository priceServiceRepository;

    private final EstateDataMapper estateDataMapper;

    ClientServiceImpl(PriceServiceRepository priceServiceRepository, EstateDataMapper estateDataMapper) {
        this.priceServiceRepository = priceServiceRepository;
        this.estateDataMapper = estateDataMapper;
    }

    @Override
    public Mono<CreateEstateResponse> createEstates(List<CreateCommand> createEstateCommands) {
        Flux<Estate> estateFlux = Flux.fromIterable(createEstateCommands.stream()
                .map(estateDataMapper::createEstateCommandToEstate)
                .toList());
        return estateFlux
                .flatMap(this::saveEstate)
                .collectList()
                .map(savedRecordsList -> new CreateEstateResponse(savedRecordsList.size()));
    }

    @Override
    public Flux<Region> fetchAllRegions() {
        return priceServiceRepository.findAllRegions();
    }

    private Mono<Estate> saveEstate(Estate estate) throws EstateDomainException {
        return priceServiceRepository.save(estate);
    }
}
