package com.real.estate.offersservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EstateData {
    @JsonProperty
    private String id;
    @JsonProperty
    private String type;
    @JsonProperty
    private String price;
    @JsonProperty
    private String description;
    @JsonProperty
    private String area;
    @JsonProperty
    private String rooms;
}
