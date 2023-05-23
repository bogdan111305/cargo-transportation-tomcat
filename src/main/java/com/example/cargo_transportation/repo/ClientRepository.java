package com.example.cargo_transportation.repo;

import com.example.cargo_transportation.entity.Client;
import com.example.cargo_transportation.modal.report.StatisticsReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query(value = "SELECT csc.clientId as clientId, " +
            "SUM(csc.count) as count, " +
            "SUM(csc.count * p.cost) as cost, " +
            "MIN(csc.incomingDate) as incomingDateMin, " +
            "MAX(csc.incomingDate) as incomingDateMax " +
            "FROM (SELECT cl.id as clientId, " +
            "c.Id as carId, " +
            "j.incoming_date as incomingDate, " +
            "s.id as serviceId, " +
            "ps.count as count " +
            "FROM client cl " +
            "INNER JOIN car c on cl.id = c.client_id " +
            "INNER JOIN journal j on c.id = j.car_id " +
            "INNER JOIN provide_service ps on j.id = ps.journal_id " +
            "INNER JOIN service s on ps.service_id = s.id " +
            "WHERE cl.id = COALESCE(:clientId, cl.id) " +
            "AND j.incoming_date BETWEEN :startDate AND :endDate) as csc " +
            "LEFT JOIN contract contr on contr.client_id = csc.clientId " +
            "AND contr.car_id = csc.carId " +
            "AND csc.incomingDate BETWEEN contr.start_date AND contr.end_date " +
            "LEFT JOIN price p on contr.id = p.contract_id " +
            "AND csc.serviceId = p.service_id " +
            "GROUP BY csc.clientId",
            nativeQuery = true)
    List<StatisticsReport> getStatisticsReport(
            @Param("clientId") Long clientId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
