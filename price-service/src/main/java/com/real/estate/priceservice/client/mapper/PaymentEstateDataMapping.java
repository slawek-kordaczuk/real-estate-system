package com.real.estate.priceservice.client.mapper;

import com.real.estate.priceservice.client.dto.RealEstateData;
import com.real.estate.priceservice.domain.dto.create.CreateCommand;
import com.real.estate.priceservice.domain.entity.EstateType;
import com.real.estate.priceservice.domain.entity.Region;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class PaymentEstateDataMapping {

    public CreateCommand estatePayloadDataToUpsertCommand(RealEstateData realEstateData, Region region) {
        return CreateCommand.builder()
                .estateId(UUID.fromString(realEstateData.getId()))
                .region(region)
                .area(Float.valueOf(realEstateData.getArea()))
                .type(EstateType.valueOf(realEstateData.getType().toUpperCase()))
                .description(realEstateData.getDescription())
                .price(new BigDecimal(realEstateData.getPrice()))
                .rooms(Integer.valueOf(realEstateData.getRooms()))
                .build();
    }
}
