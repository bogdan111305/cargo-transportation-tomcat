package com.example.cargo_transportation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Setter
@Getter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table
public class Price {
    @EmbeddedId
    @ToString.Include
    private PriceId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("contractId")
    private Contract contract;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("serviceId")
    private Service service;
    @Column(nullable = false)
    private Integer cost;

    public Price(Contract contract, Service service, Integer cost) {
        this.contract = contract;
        this.service = service;
        this.cost = cost;
        this.id = new PriceId(contract.getId(), service.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price that = (Price) o;
        return Objects.equals(service, that.service) && Objects.equals(contract, that.contract);
    }

    @Override
    public int hashCode() {
        return Objects.hash(service, contract);
    }
}