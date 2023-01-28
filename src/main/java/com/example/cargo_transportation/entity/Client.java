package com.example.cargo_transportation.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Client{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String address;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String INN;
    @Column(unique = true)
    private String KPP;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client", orphanRemoval = true)
    private List<Car> cars = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private List<Contract> contracts = new ArrayList<>();
}
