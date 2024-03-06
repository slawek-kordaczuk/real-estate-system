package com.real.estate.priceservice.domain.port.input;

import com.real.estate.priceservice.domain.dto.create.CreateCommand;
import com.real.estate.priceservice.domain.dto.create.CreateEstateResponse;
import com.real.estate.priceservice.domain.entity.Region;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {

    Mono<CreateEstateResponse> createEstates(Flux<CreateCommand> createEstateCommands);

    Flux<Region> fetchAllRegions();
}
