package com.real.estate.priceservice.controller;

import com.real.estate.priceservice.domain.dto.average.AveragePriceQuery;
import com.real.estate.priceservice.domain.dto.average.AveragePriceResponse;
import com.real.estate.priceservice.domain.dto.average.RoomSize;
import com.real.estate.priceservice.domain.port.input.PriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
class PriceServiceController {

    private final PriceService priceService;

    PriceServiceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/real-estates-stats/{regionCode}")
    public Mono<ResponseEntity<AveragePriceResponse>> averageEstatePrice(@PathVariable String regionCode,
                                                                           @RequestParam String size,
                                                                           @RequestParam String rooms,
                                                                           @RequestParam List<String> types,
                                                                           @RequestParam @DateTimeFormat(pattern = "yyyyMMdd") Date dateSince,
                                                                           @RequestParam @DateTimeFormat(pattern = "yyyyMMdd") Date dateUntil) {
        return priceService.calculateAveragePrice(AveragePriceQuery.builder()
                        .regionCode(regionCode)
                        .roomSize(RoomSize.valueOf(size))
                        .rooms(Integer.valueOf(rooms))
                        .types(typesToUpperCase(types))
                        .dataSince(convertToLocalDate(dateSince))
                        .dataUntil(convertToLocalDate(dateUntil))
                        .build())
                .map(averagePriceResponse -> ResponseEntity.ok().body(averagePriceResponse))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    private LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private List<String> typesToUpperCase(List<String> types) {
        return types.stream().map(String::toUpperCase).toList();
    }
}
