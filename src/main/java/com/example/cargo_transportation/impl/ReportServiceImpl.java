package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.modal.report.JournalReport;
import com.example.cargo_transportation.repo.JournalRepository;
import com.example.cargo_transportation.service.ReportService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
public class ReportServiceImpl implements ReportService {
    private final JournalRepository journalRepository;

    @Autowired
    public ReportServiceImpl(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    @Override
    public List<JournalReport> getJournalReport(String gosNum, Long clientId, LocalDateTime startDateReport, LocalDateTime endDateReport) {
        return journalRepository.getJournalReport(gosNum, clientId, startDateReport, endDateReport);
    }
}
