package com.real.estate.priceservice.service;

import com.real.estate.priceservice.domain.dto.average.AveragePriceQuery;
import com.real.estate.priceservice.domain.dto.average.AveragePriceResponse;
import com.real.estate.priceservice.domain.entity.Estate;
import com.real.estate.priceservice.domain.port.input.PriceService;
import com.real.estate.priceservice.domain.port.output.PriceServiceRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
class PriceServiceImpl implements PriceService {

    private final PriceServiceRepository priceServiceRepository;

    PriceServiceImpl(PriceServiceRepository priceServiceRepository) {
        this.priceServiceRepository = priceServiceRepository;
    }

    @Override
    public Mono<AveragePriceResponse> calculateAveragePrice(AveragePriceQuery averageEstateQuery, Pageable pageable) {
        return priceServiceRepository.findRegionByCode(averageEstateQuery.getRegionCode())
                .map(region -> priceServiceRepository.getAveragePriceByQuery(averageEstateQuery, pageable))
                .flatMap(flux -> flux.collectList().map(this::averagePriceFromEstates));
    }

    private AveragePriceResponse averagePriceFromEstates(List<Estate> estates) {
        if (estates.isEmpty()) {
            return AveragePriceResponse.builder()
                    .avgValue("0")
                    .build();
        }
        BigDecimal sum = BigDecimal.ZERO;
        for (Estate estate : estates) {
            sum = sum.add(estate.getPrice());
        }
        BigDecimal averagePrice = sum.divide(BigDecimal.valueOf(estates.size()), RoundingMode.HALF_EVEN)
                .setScale(2, RoundingMode.HALF_EVEN);
        return AveragePriceResponse.builder()
                .avgValue(averagePrice.toString())
                .build();
    }
}
