package com.example.cargo_transportation.service;

import com.example.cargo_transportation.modal.report.JournalReport;
import com.example.cargo_transportation.modal.report.StatisticsReport;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {
    List<JournalReport> getJournalReport(String gosNum, Long clientId,
                                         LocalDateTime startDateReport,
                                         LocalDateTime endDateReport);

    List<StatisticsReport> getStatisticsReport(Long clientId,
                                               LocalDateTime startDateReport,
                                               LocalDateTime endDateReport);
}
