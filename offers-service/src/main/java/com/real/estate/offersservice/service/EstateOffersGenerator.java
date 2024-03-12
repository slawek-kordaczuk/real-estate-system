package com.real.estate.offersservice.service;

import com.real.estate.offersservice.entity.EstateOffer;
import com.real.estate.offersservice.entity.EstateType;
import com.real.estate.offersservice.entity.RegionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Component
class EstateOffersGenerator {

    private final Random random;

    private final EstateType[] estateTypes = EstateType.values();

    public EstateOffersGenerator(Random random) {
        this.random = random;
    }

    public Flux<EstateOffer> createEstateOffers(RegionCode region) {
        List<EstateOffer> offers = new ArrayList<>();
        var numberOfPages = random.nextInt(90 - 80) + 80;
        for (int i = 1; i <= numberOfPages; i++) {
            offers.add(createEstateOffer(region));
        }
        return Flux.fromIterable(offers);
    }

    private EstateOffer createEstateOffer(RegionCode region){
        return EstateOffer.builder()
                .id(UUID.randomUUID())
                .regionCode(region)
                .description("some description " + random.nextInt())
                .area(random.nextFloat(400))
                .rooms(random.nextInt(6))
                .price(BigDecimal.valueOf(random.nextFloat()  * (3000000 - 100000) + 100000)
                        .setScale(2, RoundingMode.HALF_EVEN))
                .type(estateTypes[random.nextInt(estateTypes.length)])
                .createdDate(LocalDate.now())
                .build();
    }

}
