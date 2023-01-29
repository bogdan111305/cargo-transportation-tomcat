package com.example.cargo_transportation.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String gosNum;
    @Column(nullable = false, unique = true)
    private String STS;
    @Column(nullable = false)
    private String model;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "car")
    private List<Contract> contracts = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "car")
    private List<Journal> journals = new ArrayList<>();
}
