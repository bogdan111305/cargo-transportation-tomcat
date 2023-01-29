package com.example.cargo_transportation.repo;

import com.example.cargo_transportation.entity.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRepository extends JpaRepository<Journal, Long> {
}