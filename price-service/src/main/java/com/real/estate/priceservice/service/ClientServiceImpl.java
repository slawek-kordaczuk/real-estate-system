package com.real.estate.priceservice.service;

import com.real.estate.priceservice.domain.dto.create.CreateCommand;
import com.real.estate.priceservice.domain.dto.create.CreateEstateResponse;
import com.real.estate.priceservice.domain.entity.Region;
import com.real.estate.priceservice.domain.port.input.ClientService;
import com.real.estate.priceservice.domain.port.output.PriceServiceRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ClientServiceImpl implements ClientService {

    private final PriceServiceRepository priceServiceRepository;

    ClientServiceImpl(PriceServiceRepository priceServiceRepository) {
        this.priceServiceRepository = priceServiceRepository;
    }

    @Override
    public CreateEstateResponse upsertEstates(List<CreateCommand> createEstateCommands) {
        return null;
    }

    @Override
    public List<Region> fetchAllRegions() {
        return null;
    }
}
