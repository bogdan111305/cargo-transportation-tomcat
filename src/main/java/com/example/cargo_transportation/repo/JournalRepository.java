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
            "WHERE j.status = COALESCE(:status, j.status) and " +
            "j.car.gosNum = COALESCE(:gosNum, j.car.gosNum) and " +
            "j.car.sts = COALESCE(:sts, j.car.sts) and " +
            "j.car.client.id = COALESCE(:clientId, j.car.client.id) and " +
            "j.incomingDate between :startDate and :endDate")
    List<Journal> findJournalsByFilters(
            @Param("status") JournalStatus status,
            @Param("gosNum") String gosNum,
            @Param("sts") String sts,
            @Param("clientId") Long clientId,
            @Param("startDate") LocalDateTime startDateReport,
            @Param("endDate") LocalDateTime endDateReport
    );
}