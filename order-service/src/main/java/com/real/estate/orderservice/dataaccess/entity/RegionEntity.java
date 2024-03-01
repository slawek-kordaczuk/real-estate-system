package com.real.estate.orderservice.dataaccess.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "region")
@Entity
public class RegionEntity {

    @Id
    private Integer id;
    private String regionCode;
    private String description;

    @OneToMany(mappedBy = "region", cascade = CascadeType.DETACH)
    private List<EstateEntity> estates;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegionEntity that = (RegionEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
