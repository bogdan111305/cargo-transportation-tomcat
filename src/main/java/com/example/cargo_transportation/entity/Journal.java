package com.example.cargo_transportation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime incomingDate;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime outPlanDate;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime outFactDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private Car car;

    @OneToMany(mappedBy = "journal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GetService> getServices = new ArrayList<>();

    public void addService(Service service, Integer count) {
        GetService getService = new GetService(service, this, count);
        getServices.add(getService);
        service.getGetServices().add(getService);
    }

    public void removeService(Service service) {
        GetService getService = getServices.stream()
                .filter(rf -> rf.getJournal().equals(this) && rf.getService().equals(service))
                .findFirst()
                .orElse(null);
        if (getService != null) {
            getService.getService().getGetServices().remove(getService);
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
