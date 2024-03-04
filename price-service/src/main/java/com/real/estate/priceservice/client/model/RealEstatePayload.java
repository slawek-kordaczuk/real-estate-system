package com.real.estate.priceservice.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class RealEstatePayload {
    @JsonProperty
    private String totalPages;
    @JsonProperty
    private List<RealEstateData> data;
}
