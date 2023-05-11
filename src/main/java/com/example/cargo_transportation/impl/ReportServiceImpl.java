package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.modal.report.StatisticsReport;
import com.example.cargo_transportation.repo.ClientRepository;
import com.example.cargo_transportation.service.ReportService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.cargo_transportation.common.Common.DEFAULT_END_DATE;
import static com.example.cargo_transportation.common.Common.DEFAULT_START_DATE;

@Service
@Log4j2
public class ReportServiceImpl implements ReportService {
    private final ClientRepository clientRepository;

    @Autowired
    public ReportServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<StatisticsReport> getStatisticsReport(Long clientId, LocalDateTime startDateReport, LocalDateTime endDateReport) {
        if (startDateReport == null) startDateReport = DEFAULT_START_DATE;
        if (endDateReport == null) endDateReport = DEFAULT_END_DATE;
        return clientRepository.getStatisticsReport(clientId, startDateReport, endDateReport);
    }
}
