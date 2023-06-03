package com.example.cargo_transportation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table
public class ProvideService {
    @EmbeddedId
    @ToString.Include
    private ProvideServiceId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("journalId")
    private Journal journal;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("serviceId")
    private Service service;

    @Column(nullable = false)
    private Integer count;

    public ProvideService(Service service, Journal journal, Integer count) {
        this.service = service;
        this.journal = journal;
        this.count = count;
        this.id = new ProvideServiceId(journal.getId(), service.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProvideService that = (ProvideService) o;
        return Objects.equals(service, that.service) && Objects.equals(journal, that.journal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(service, journal);
    }
}