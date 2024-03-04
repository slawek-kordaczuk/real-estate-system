package com.real.estate.priceservice.domain.dto.average;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AveragePriceResponse {

    @JsonProperty
    private String avgValue;
}
