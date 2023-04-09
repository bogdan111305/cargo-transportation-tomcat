package com.example.cargo_transportation.service;

import com.example.cargo_transportation.modal.report.JournalReport;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {
    List<JournalReport> getJournalReport(String gosNum, Long clientId,
                                         LocalDateTime startDateReport,
                                         LocalDateTime endDateReport);
}
