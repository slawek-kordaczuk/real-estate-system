package com.real.estate.priceservice.service;

import com.real.estate.priceservice.domain.dto.create.CreateCommand;
import com.real.estate.priceservice.domain.dto.create.CreateEstateResponse;
import com.real.estate.priceservice.domain.entity.Region;
import com.real.estate.priceservice.domain.port.input.ClientService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class ClientServiceImpl implements ClientService {

    @Override
    public CreateEstateResponse upsertEstates(List<CreateCommand> createEstateCommands) {
        return null;
    }

    @Override
    public List<Region> fetchAllRegions() {
        return null;
    }
}
