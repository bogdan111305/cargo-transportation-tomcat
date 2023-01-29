package com.example.cargo_transportation.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class RenderFavor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Favor favor;
    @ManyToOne(fetch = FetchType.LAZY)
    private Journal journal;
    @Column(nullable = false)
    private Integer count;
}
