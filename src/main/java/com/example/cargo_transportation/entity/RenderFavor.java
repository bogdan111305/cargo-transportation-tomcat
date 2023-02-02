package com.example.cargo_transportation.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table
public class RenderFavor {
    @EmbeddedId
    private RenderFavorId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("journalId")
    private Journal journal;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("favorId")
    private Favor favor;

    @Column(nullable = false)
    private Integer count;

    public RenderFavor(Favor favor, Journal journal, Integer count) {
        this.favor = favor;
        this.journal = journal;
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RenderFavor that = (RenderFavor) o;
        return Objects.equals(favor, that.favor) && Objects.equals(journal, that.journal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(favor, journal);
    }
}
