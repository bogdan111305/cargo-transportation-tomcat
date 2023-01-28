package com.example.cargo_transportation.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class RenderService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Service service;
    @ManyToOne(fetch = FetchType.LAZY)
    private Journal journal;
    @Column(nullable = false)
    private Integer count;
}
