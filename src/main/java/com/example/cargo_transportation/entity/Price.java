package com.example.cargo_transportation.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Favor favor;
    @ManyToOne(fetch = FetchType.LAZY)
    private Contract contract;
    @Column(nullable = false)
    private Integer cost;
}