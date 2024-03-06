package com.real.estate.priceservice.domain.port.input;

import com.real.estate.priceservice.domain.dto.average.AveragePriceQuery;
import com.real.estate.priceservice.domain.dto.average.AveragePriceResponse;
import reactor.core.publisher.Mono;

public interface PriceService {
    Mono<AveragePriceResponse> calculateAveragePrice(AveragePriceQuery averageEstateQuery);
}
