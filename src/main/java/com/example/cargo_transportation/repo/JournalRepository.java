package com.example.cargo_transportation.repo;

import com.example.cargo_transportation.entity.Journal;
import com.example.cargo_transportation.modal.report.JournalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JournalRepository extends JpaRepository<Journal, Long> {
    @Query(value = "SELECT j \n" +
            "FROM Journal j \n" +
            "JOIN Car c on j.car.id = c.id \n" +
            "WHERE c.id = COALESCE(:carId, c.id) and \n" +
            "c.gosNum = COALESCE(:gosNum, c.gosNum) and \n" +
            "c.sts = COALESCE(:sts, c.sts)")
    List<Journal> findUnclosedJournals(@Param("carId") Long carId,
                                       @Param("carId") String gosNum,
                                       @Param("carId") String sts);

    @Query(value = "SELECT j.waybill as waybill, \n" +
                    "       j.incoming_date as incomingDateTime, \n" +
                    "       c.gos_num as gosNum, \n" +
                    "       s.name as service \n" +
                    "FROM journal as j \n" +
                    "JOIN car as c on j.car_id = c.id \n" +
                    "JOIN client as cl on c.client_id = cl.id \n" +
                    "LEFT JOIN get_service as gs on j.id = gs.journal_id \n" +
                    "LEFT JOIN service as s on gs.service_id = s.id \n" +
                    "WHERE c.gos_num = COALESCE(:gosNum, c.gos_num) and \n" +
                    "cl.id = COALESCE(:clientId, cl.id) and \n" +
                    "j.incoming_date between :startDate and :endDate",
            nativeQuery = true)
    List<JournalReport> getJournalReport(@Param("gosNum") String gosNum,
                                         @Param("clientId") Long clientId,
                                         @Param("startDate") LocalDateTime startDateReport,
                                         @Param("endDate") LocalDateTime endDateReport);
}