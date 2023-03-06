package com.example.cargo_transportation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime incomingDate;
    @Column(nullable = false)
    private LocalDateTime outPlanDate;
    private LocalDateTime outFactDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private Car car;
    private String waybill;
    private String nameDriver;

    @OneToMany(mappedBy = "journal",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<GetService> getServices = new ArrayList<>();

    public void addService(Service service, Integer count) {
        GetService getService = new GetService(service, this, count);
        getServices.add(getService);
    }

    public void removeService(Service service) {
        GetService getService = getServices.stream()
                .filter(rf -> rf.getJournal().equals(this) && rf.getService().equals(service))
                .findFirst()
                .orElse(null);
        if (getService != null) {
            getService.setService(null);
            getService.setJournal(null);
            this.getServices.remove(getService);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Journal journal = (Journal) o;
        return Objects.equals(id, journal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, car);
    }
}
