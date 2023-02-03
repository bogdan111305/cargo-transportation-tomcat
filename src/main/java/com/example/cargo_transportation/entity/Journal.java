package com.example.cargo_transportation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    private List<RenderFavor> renderFavors = new ArrayList<>();

    public void addFavor(Favor favor, Integer count) {
        RenderFavor renderFavor = new RenderFavor(favor, this, count);
        renderFavors.add(renderFavor);
        favor.getRenderFavors().add(renderFavor);
    }

    public void removeFavor(Favor favor) {
        RenderFavor renderFavor = renderFavors.stream()
                .filter(rf -> rf.getJournal().equals(this) && rf.getFavor().equals(favor))
                .findFirst()
                .orElse(null);
        if (renderFavor != null) {
            renderFavor.getFavor().getRenderFavors().remove(renderFavor);
            renderFavor.setFavor(null);
            renderFavor.setJournal(null);
            this.renderFavors.remove(renderFavor);
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
