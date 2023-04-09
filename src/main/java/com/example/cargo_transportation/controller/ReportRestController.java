package com.example.cargo_transportation.controller;

import com.example.cargo_transportation.modal.report.JournalReport;
import com.example.cargo_transportation.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportRestController {
    private ReportService reportService;

    @Autowired
    public ReportRestController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/journal")
    public List<JournalReport> getJournalReport(@RequestParam(value = "gosNum", required = false) String gosNum,
                                                @RequestParam(value = "clientId", required = false) Long clientId,
                                                @RequestParam(value = "startDate", required = false)
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                        LocalDateTime startDate,
                                                @RequestParam(value = "endDate", required = false)
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                        LocalDateTime endDate) {
        return reportService.getJournalReport(gosNum, clientId, startDate, endDate);
    }
}
