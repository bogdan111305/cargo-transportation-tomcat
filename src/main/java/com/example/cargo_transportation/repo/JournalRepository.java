package com.example.cargo_transportation.repo;

import com.example.cargo_transportation.entity.Journal;
import com.example.cargo_transportation.modal.report.JournalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JournalRepository extends JpaRepository<Journal, Long> {
    @Query(nativeQuery = true,
            value = "SELECT j.waybill as waybill, \n" +
                    "       j.incoming_date as incomingDateTime, \n" +
                    "       c.gos_num as gosNum, \n" +
                    "       s.name as service \n" +
                    "FROM journal as j \n" +
                    "JOIN car as c on j.car_id = c.id \n" +
                    "LEFT JOIN get_service as gs on j.id = gs.journal_id \n" +
                    "LEFT JOIN service as s on gs.service_id = s.id \n" +
                    "WHERE c.gos_num = COALESCE(:gosNum, c.gos_num)")
    List<JournalReport> getJournalReport(@Param("gosNum") String gosNum);
}