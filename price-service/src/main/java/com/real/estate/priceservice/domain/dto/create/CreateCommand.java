package com.real.estate.priceservice.domain.dto.create;

import com.real.estate.priceservice.domain.entity.EstateType;
import com.real.estate.priceservice.domain.entity.Region;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
public class CreateCommand {
    private UUID estateId;
    private Region region;
    private EstateType type;
    private BigDecimal price;
    private String description;
    private Float area;
    private Integer rooms;
}
