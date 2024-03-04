package com.real.estate.priceservice.domain.dto.average;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AveragePriceQuery {

    private String regionCode;
    private RoomSize roomSize;
    private List<String> types;
    private Integer rooms;
    private LocalDate dataSince;
    private LocalDate dataUntil;

}
