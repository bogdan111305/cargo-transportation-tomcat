package com.example.cargo_transportation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GetService> getServices = new ArrayList<>();
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Price> prices = new ArrayList<>();

    public void addJournal(Journal journal, Integer count) {
        GetService getService = new GetService(this, journal, count);
        getServices.add(getService);
        journal.getGetServices().add(getService);
    }

    public void removeJournal(Journal journal) {
        GetService getService = getServices.stream()
                .filter(rf -> rf.getService().equals(this) && rf.getJournal().equals(journal))
                .findFirst()
                .orElse(null);
        if (getService != null) {
            getService.getJournal().getGetServices().remove(getService);
            getService.setJournal(null);
            getService.setService(null);
            this.getServices.remove(getService);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return Objects.equals(id, service.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
