package com.example.cargo_transportation.repo;

import com.example.cargo_transportation.entity.Client;
import com.example.cargo_transportation.modal.report.JournalReport;
import com.example.cargo_transportation.modal.report.StatisticsReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findClientById(Long id);

    @Query( value =
            "SELECT csc.clientId as clientId, sum(csc.count) as count, sum(csc.count * p.cost) as cost, " +
            "min(csc.incomingDate) as incomingDateMin, max(csc.incomingDate) as incomingDateMax \n" +
            "FROM (SELECT cl.id as clientId, c.Id as carId, j.incoming_date as incomingDate,\n" +
            "             s.id as serviceId, gs.count as count \n" +
            "        FROM client cl \n" +
            "        INNER JOIN car c on cl.id = c.client_id \n" +
            "        INNER JOIN journal j on c.id = j.car_id \n" +
            "        INNER JOIN get_service gs on j.id = gs.journal_id \n" +
            "        INNER JOIN service s on gs.service_id = s.id \n" +
            "        WHERE cl.Id = COALESCE(:clientId, cl.id) and \n" +
            "              j.incoming_date between :startDate and :endDate \n" +
            "    ) as csc\n" +
            "LEFT JOIN contract contr on contr.client_id = csc.clientId and\n" +
            "                            contr.car_id = csc.carId and\n" +
            "                            csc.incomingDate between contr.start_date and contr.end_date\n" +
            "LEFT JOIN price p on contr.id = p.contract_id and csc.serviceId = p.service_id\n" +
            "GROUP BY csc.clientId",
            nativeQuery = true)
    List<StatisticsReport> getStatisticsReport(@Param("clientId") Long clientId,
                                               @Param("startDate") LocalDateTime startDateReport,
                                               @Param("endDate") LocalDateTime endDateReport);
}