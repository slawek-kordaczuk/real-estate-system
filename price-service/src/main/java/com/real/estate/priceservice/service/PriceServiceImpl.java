package com.real.estate.priceservice.service;

import com.real.estate.priceservice.domain.dto.average.AveragePriceQuery;
import com.real.estate.priceservice.domain.dto.average.AveragePriceResponse;
import com.real.estate.priceservice.domain.entity.Region;
import com.real.estate.priceservice.domain.port.input.PriceService;
import com.real.estate.priceservice.domain.port.output.PriceServiceRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
class PriceServiceImpl implements PriceService {

    private final PriceServiceRepository priceServiceRepository;

    PriceServiceImpl(PriceServiceRepository priceServiceRepository) {
        this.priceServiceRepository = priceServiceRepository;
    }

    @Override
    public Mono<AveragePriceResponse> calculateAveragePrice(AveragePriceQuery averageEstateQuery) {
        return priceServiceRepository.findRegionByCode(averageEstateQuery.getRegionCode())
                .flatMap(region -> priceServiceRepository.getAveragePriceByQuery(averageEstateQuery))
                .flatMap(averagePrice -> Mono.just(new AveragePriceResponse(averagePrice.toString())));
    }
}
