package com.real.estate.priceservice.service.mapper;

import com.real.estate.priceservice.domain.dto.create.CreateCommand;
import com.real.estate.priceservice.domain.entity.Estate;
import org.springframework.stereotype.Component;

@Component
public class EstateDataMapper {

    public Estate createEstateCommandToEstate(CreateCommand createCommand){
        return Estate.builder()
                .id(createCommand.getEstateId())
                .regionCode(createCommand.getRegion().getRegionCode())
                .area(createCommand.getArea())
                .type(createCommand.getType())
                .description(createCommand.getDescription())
                .rooms(createCommand.getRooms())
                .price(createCommand.getPrice())
                .build();
    }
}
