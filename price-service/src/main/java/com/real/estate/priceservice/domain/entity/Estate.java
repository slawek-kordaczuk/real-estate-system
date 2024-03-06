package com.real.estate.priceservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "estate")
@Entity
public class Estate {

    @Id
    private Integer id;
    private String regionCode;
    @Enumerated(EnumType.STRING)
    private EstateType type;
    private BigDecimal price;
    private String description;
    private Float area;
    private Integer rooms;
    private LocalDate createdDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estate that = (Estate) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
