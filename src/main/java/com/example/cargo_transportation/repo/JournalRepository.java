package com.example.cargo_transportation.repo;

import com.example.cargo_transportation.entity.Journal;
import com.example.cargo_transportation.entity.enums.JournalStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JournalRepository extends JpaRepository<Journal, Long> {
    @EntityGraph(attributePaths = {"car.client", "provideServices"})
    @Query("SELECT j " +
            "FROM Journal j " +
            "WHERE (:status IS NULL OR j.status = :status) " +
            "AND (:gosNum IS NULL OR j.car.gosNum = :gosNum) " +
            "AND (:sts IS NULL OR j.car.sts = :sts) " +
            "AND (:clientId IS NULL OR j.car.client.id = :clientId) " +
            "AND j.incomingDate BETWEEN :startDate AND :endDate")
    List<Journal> findJournalsByFilters(
            @Param("status") JournalStatus status,
            @Param("gosNum") String gosNum,
            @Param("sts") String sts,
            @Param("clientId") Long clientId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
