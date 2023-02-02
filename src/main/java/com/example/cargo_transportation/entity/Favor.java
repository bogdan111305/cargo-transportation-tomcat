package com.example.cargo_transportation.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table
public class Favor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "favor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RenderFavor> renderFavors = new ArrayList<>();
    @OneToMany(mappedBy = "favor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Price> prices = new ArrayList<>();

    public void addJournal(Journal journal, Integer count) {
        RenderFavor renderFavor = new RenderFavor(this, journal, count);
        renderFavors.add(renderFavor);
        journal.getRenderFavors().add(renderFavor);
    }

    public void removeJournal(Journal journal) {
        List<RenderFavor> removedRenderFavors = new ArrayList<>();
        for (RenderFavor renderFavor : renderFavors) {
            if (renderFavor.getFavor().equals(this) && renderFavor.getJournal().equals(journal)) {
                removedRenderFavors.add(renderFavor);
                renderFavor.getJournal().getRenderFavors().remove(renderFavor);
                renderFavor.setJournal(null);
                renderFavor.setFavor(null);
            }
        }
        renderFavors.removeAll(removedRenderFavors);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favor favor = (Favor) o;
        return Objects.equals(id, favor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
