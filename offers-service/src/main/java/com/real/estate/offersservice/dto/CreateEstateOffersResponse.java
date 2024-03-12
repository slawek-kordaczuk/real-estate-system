package com.real.estate.offersservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@JsonDeserialize(builder = CreateEstateOffersResponse.CreateEstateOffersResponseBuilder.class)
public class CreateEstateOffersResponse {
    @JsonProperty
    private String numberOfCreatedOffers;
}
