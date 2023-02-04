package com.example.cargo_transportation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime endDate;
    @Column(nullable = false)
    private boolean defaultPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Car car;
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Price> prices = new ArrayList<>();

    public void addPrice(Service service, Integer cost) {
        Price price = new Price(this, service, cost);
        prices.add(price);
        service.getPrices().add(price);
    }

    public void removePrice(Service service) {
        Price price = prices.stream()
                .filter(rf -> rf.getContract().equals(this) && rf.getService().equals(service))
                .findFirst()
                .orElse(null);
        if (price != null) {
            price.getService().getPrices().remove(price);
            price.setService(null);
            price.setContract(null);
            this.prices.remove(price);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Contract contract = (Contract) o;
        return Objects.equals(id, contract.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, car, client);
    }
}
