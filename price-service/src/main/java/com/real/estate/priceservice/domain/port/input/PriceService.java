package com.real.estate.priceservice.domain.port.input;

import com.real.estate.priceservice.domain.dto.average.AveragePriceQuery;
import com.real.estate.priceservice.domain.dto.average.AveragePriceResponse;

public interface PriceService {
    AveragePriceResponse calculateAveragePrice(AveragePriceQuery averageEstateQuery);
}
