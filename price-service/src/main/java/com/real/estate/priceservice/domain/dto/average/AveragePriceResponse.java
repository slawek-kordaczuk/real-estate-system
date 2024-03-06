package com.real.estate.priceservice.domain.dto.average;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@JsonDeserialize(builder = AveragePriceResponse.AveragePriceResponseBuilder.class)
public class AveragePriceResponse {

    @JsonProperty
    private String avgValue;
}
