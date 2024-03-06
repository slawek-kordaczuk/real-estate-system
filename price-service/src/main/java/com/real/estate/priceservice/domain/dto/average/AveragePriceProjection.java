package com.real.estate.priceservice.domain.dto.average;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AveragePriceProjection {
    private Double price;

    public AveragePriceProjection(Double price) {
        this.price = price;
    }
}
