package com.example.cargo_transportation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;
    @Column(nullable = false,
            unique = true)
    private String gosNum;
    @Column(nullable = false,
            unique = true)
    private String sts;
    @Column(nullable = false)
    private String model;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "car")
    private List<Contract> contracts = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "car")
    private List<Journal> journals = new ArrayList<>();
}
