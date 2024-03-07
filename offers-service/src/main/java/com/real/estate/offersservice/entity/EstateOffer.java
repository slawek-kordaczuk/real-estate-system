package com.real.estate.offersservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
@Validated
public class EstateOffer {

    @Id
    private UUID id;
    private String regionCode;
    private EstateType type;
    private BigDecimal price;
    private String description;
    private Float area;
    private Integer rooms;
    private LocalDate createdDate;

}
