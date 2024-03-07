package com.real.estate.offersservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RegionEstateOffers {
    @JsonProperty
    private String totalPages;
    @JsonProperty
    private List<EstateData> data;
}
