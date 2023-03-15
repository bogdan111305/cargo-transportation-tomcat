package com.example.cargo_transportation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@ToString(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PriceId implements Serializable {
    @Column
    @ToString.Include
    private Long contractId;
    @Column
    @ToString.Include
    private Long serviceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceId that = (PriceId) o;
        return Objects.equals(contractId, that.contractId) && Objects.equals(serviceId, that.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contractId, serviceId);
    }
}
