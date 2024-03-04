package com.real.estate.priceservice.service;

import com.real.estate.priceservice.domain.dto.average.AveragePriceQuery;
import com.real.estate.priceservice.domain.dto.average.AveragePriceResponse;
import com.real.estate.priceservice.domain.port.input.PriceService;
import org.springframework.stereotype.Service;

@Service
class PriceServiceImpl implements PriceService {



    @Override
    public AveragePriceResponse calculateAveragePrice(AveragePriceQuery averageEstateQuery) {
        return null;
    }
}
