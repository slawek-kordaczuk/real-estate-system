package com.real.estate.priceservice.domain.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateEstateResponse {
    private final Integer numberOfCreatedEstates;
}
