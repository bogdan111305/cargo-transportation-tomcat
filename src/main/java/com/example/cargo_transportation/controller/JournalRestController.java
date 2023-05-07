package com.example.cargo_transportation.controller;

import com.example.cargo_transportation.entity.enums.JournalStatus;
import com.example.cargo_transportation.modal.dto.JournalRequest;
import com.example.cargo_transportation.modal.dto.GetServiceDTO;
import com.example.cargo_transportation.modal.dto.JournalResponse;
import com.example.cargo_transportation.service.JournalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/journal")
public class JournalRestController {
    private final JournalService journalService;

    @Autowired
    public JournalRestController(JournalService journalService) {
        this.journalService = journalService;
    }

    @GetMapping("/{journalId}")
    public JournalResponse getJournalById(@PathVariable Long journalId) {
        return journalService.getJournalById(journalId);
    }

    @GetMapping("/journals")
    public List<JournalResponse> getJournals(@RequestParam(name = "status", required = false) JournalStatus status,
                                             @RequestParam(name = "gosNum", required = false) String gosNum,
                                             @RequestParam(name = "sts", required = false) String sts) {
        return journalService.getJournals(status, gosNum, sts);
    }

    @GetMapping("/{journalId}/services")
    public List<GetServiceDTO> getServicesFromJournal(@PathVariable Long journalId) {
        return journalService.getServicesFromJournal(journalId);
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

    @PutMapping("/status/{journalId}")
    public JournalResponse updateJournalStatus(@PathVariable Long journalId,
                                               @RequestParam JournalStatus status) {
        return journalService.updateJournalStatus(journalId, status);
    }

    @DeleteMapping("/{journalId}")
    public void deleteJournal(@PathVariable Long journalId) {
        journalService.deleteJournal(journalId);
    }

    @PostMapping("/{journalId}/services")
    public List<GetServiceDTO> addServicesFromJournal(@PathVariable Long journalId,
                                                      @RequestBody List<GetServiceDTO> services) {
        return journalService.addServicesFromJournal(journalId, services);
    }

    @PostMapping("/{journalId}/service/{serviceId}")
    public void addServiceFromJournal(@PathVariable("journalId") Long journalId,
                                      @PathVariable("serviceId") Long serviceId,
                                      @RequestParam Integer count) {
        journalService.addServiceFromJournal(journalId, serviceId, count);
    }

    @DeleteMapping("/{journalId}/service/{serviceId}")
    public void removeServiceFromJournal(@PathVariable("journalId") Long journalId,
                                         @PathVariable("serviceId") Long serviceId) {
        journalService.removeServiceFromJournal(journalId, serviceId);
    }

    @GetMapping("/statuses")
    public List<JournalStatus> getJournalStatuses() {
        return journalService.getJournalStatuses();
    }
}
