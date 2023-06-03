package com.example.cargo_transportation.controller;

import com.example.cargo_transportation.entity.enums.JournalStatus;
import com.example.cargo_transportation.modal.dto.JournalRequest;
import com.example.cargo_transportation.modal.dto.ProvideServiceRequest;
import com.example.cargo_transportation.modal.dto.JournalResponse;
import com.example.cargo_transportation.modal.dto.ProvideServiceResponse;
import com.example.cargo_transportation.service.JournalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/journal")
public class JournalRestController {
    private final JournalService journalService;

    @Autowired
    public JournalRestController(JournalService journalService) {
        this.journalService = journalService;
    }

    @GetMapping("/statuses")
    public List<JournalStatus> getJournalStatuses() {
        return journalService.getJournalStatuses();
    }

    @GetMapping("/{journalId}")
    public JournalResponse getJournalById(@PathVariable Long journalId) {
        return journalService.getJournalById(journalId);
    }

    @GetMapping("/journals")
    public List<JournalResponse> getJournals(@RequestParam(name = "status", required = false) JournalStatus status,
                                             @RequestParam(name = "gosNum", required = false) String gosNum,
                                             @RequestParam(name = "sts", required = false) String sts,
                                             @RequestParam(value = "clientId", required = false) Long clientId,
                                             @RequestParam(value = "startDate", required = false)
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                         LocalDateTime startDate,
                                             @RequestParam(value = "endDate", required = false)
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                         LocalDateTime endDate) {
        return journalService.getJournals(status, gosNum, sts, clientId, startDate, endDate);
    }

    @PostMapping()
    public JournalResponse createJournal(@Valid @RequestBody JournalRequest journal) {
        return journalService.createJournal(journal);
    }

    @PutMapping("/{journalId}")
    public JournalResponse updateJournal(@Valid @RequestBody(required = false) JournalRequest journal,
                                        @PathVariable Long journalId) {
        return journalService.updateJournal(journal, journalId);
    }

    @PutMapping("/{journalId}/status/{status}")
    public void updateJournalStatus(@PathVariable("journalId") Long journalId,
                                    @PathVariable("status") JournalStatus status) {
        journalService.updateJournalStatus(journalId, status);
    }

    @DeleteMapping("/{journalId}")
    public void deleteJournal(@PathVariable Long journalId) {
        journalService.deleteJournal(journalId);
    }

    @PostMapping("/{journalId}/service/{serviceId}")
    public void addServiceFromJournal(@PathVariable("journalId") Long journalId,
                                      @PathVariable("serviceId") Long serviceId,
                                      @RequestParam("count") Integer count) {
        journalService.addServiceFromJournal(journalId, serviceId, count);
    }

    @DeleteMapping("/{journalId}/service/{serviceId}")
    public void removeServiceFromJournal(@PathVariable("journalId") Long journalId,
                                         @PathVariable("serviceId") Long serviceId) {
        journalService.removeServiceFromJournal(journalId, serviceId);
    }
}
