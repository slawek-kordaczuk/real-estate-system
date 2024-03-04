package com.real.estate.priceservice.domain.port.input;

import com.real.estate.priceservice.domain.dto.create.CreateCommand;
import com.real.estate.priceservice.domain.dto.create.CreateEstateResponse;
import com.real.estate.priceservice.domain.entity.Region;

import java.util.List;

public interface ClientService {

    CreateEstateResponse upsertEstates(List<CreateCommand> createEstateCommands);

    List<Region> fetchAllRegions();
}
