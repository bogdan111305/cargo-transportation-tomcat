package com.example.cargo_transportation.repo;

import com.example.cargo_transportation.entity.Journal;
import com.example.cargo_transportation.modal.report.JournalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JournalRepository extends JpaRepository<Journal, Long> {
    @Query(value =
            "SELECT j " +
            "FROM Journal j " +
            "JOIN Car c on j.car.id = c.id " +
            "WHERE j.status = 'OPEN' and " +
            "c.id = COALESCE(:carId, c.id) and " +
            "c.gosNum = COALESCE(:gosNum, c.gosNum) and " +
            "c.sts = COALESCE(:sts, c.sts)")
    List<Journal> findOpenJournals(@Param("carId") Long carId,
                                   @Param("gosNum") String gosNum,
                                   @Param("sts") String sts);

    @Query(value =
            "SELECT j.waybill as waybill, j.incomingDate as incomingDateTime, " +
            "       c.gosNum as gosNum, s.name as service " +
            "FROM Journal as j " +
            "JOIN Car as c on j.car.id = c.id " +
            "JOIN Client as cl on c.client.id = cl.id " +
            "LEFT JOIN GetService as gs on gs.journal.id = j.id " +
            "LEFT JOIN Service as s on gs.service.id = s.id " +
            "WHERE c.gosNum = COALESCE(:gosNum, c.gosNum) and " +
            "cl.id = COALESCE(:clientId, cl.id) and " +
            "j.incomingDate between :startDate and :endDate")
    List<JournalReport> getJournalReport(@Param("gosNum") String gosNum,
                                         @Param("clientId") Long clientId,
                                         @Param("startDate") LocalDateTime startDateReport,
                                         @Param("endDate") LocalDateTime endDateReport);
}