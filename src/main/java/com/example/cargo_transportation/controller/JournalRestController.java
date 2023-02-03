package com.example.cargo_transportation.controller;

import com.example.cargo_transportation.dto.ServiceDTO;
import com.example.cargo_transportation.dto.JournalDTO;
import com.example.cargo_transportation.dto.GetServiceDTO;
import com.example.cargo_transportation.service.JournalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/journal")
public class JournalRestController {

    private final JournalService journalService;

    @Autowired
    public JournalRestController(JournalService journalService) {
        this.journalService = journalService;
    }

    @GetMapping("/journals")
    public List<JournalDTO> getAllJournal(@RequestBody List<Long> ids) {
        return journalService.getAllJournal(ids);
    }

    @GetMapping("/{journalId}")
    public JournalDTO getJournalById(@PathVariable Long journalId) {
        return journalService.getJournalById(journalId);
    }

    @PostMapping()
    public JournalDTO createJournal(@Valid @RequestBody JournalDTO journalDTO) {
        return journalService.createJournal(journalDTO);
    }

    @PutMapping("/{journalId}")
    public JournalDTO updateJournal(@Valid @RequestBody JournalDTO journalDTO,
                                    @PathVariable Long journalId) {
        return journalService.updateJournal(journalDTO, journalId);
    }

    @DeleteMapping("/{journalId}")
    public void deleteJournal(@PathVariable Long journalId) {
        journalService.deleteJournal(journalId);
    }

    @GetMapping("/{journalId}/services")
    public List<GetServiceDTO> getServicesFromJournal(@PathVariable Long journalId) {
        return journalService.getServicesFromJournal(journalId);
    }

    @PostMapping("/{journalId}/services")
    public void addServicesFromJournal(@PathVariable Long journalId,
                                                 @RequestBody List<GetServiceDTO> services) {
        journalService.addServicesFromJournal(journalId, services);
    }

    @PostMapping("/{journalId}/service/{serviceId}")
    public void addServiceFromJournal(@PathVariable("journalId") Long journalId,
                                          @PathVariable("serviceId") Long serviceId,
                                          @RequestBody Integer count) {
        journalService.addServiceFromJournal(journalId, serviceId, count);
    }

    @DeleteMapping("/{journalId}/service/{serviceId}")
    public void removeServiceFromJournal(@PathVariable("journalId") Long journalId,
                                       @PathVariable("serviceId") Long serviceId) {
        journalService.removeServiceFromJournal(journalId, serviceId);
    }
}
