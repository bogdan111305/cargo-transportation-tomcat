package com.example.cargo_transportation.controller;

import com.example.cargo_transportation.modal.dto.JournalDTO;
import com.example.cargo_transportation.modal.dto.GetServiceDTO;
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

    @GetMapping("/{journalId}")
    public JournalDTO getJournalById(@PathVariable Long journalId) {
        return journalService.getJournalById(journalId);
    }

    @GetMapping("/journals")
    public List<JournalDTO> getAllJournal(@RequestBody(required = false) List<Long> ids) {
        return journalService.getAllJournal(ids);
    }

    @GetMapping("/findUncloseJournals")
    public List<JournalDTO> getUnclosedJournal(@RequestParam(name = "carId", required = false) Long carId,
                                               @RequestParam(name = "gosNum", required = false) String gosNum,
                                               @RequestParam(name = "sts", required = false) String sts) {
        return journalService.getUnclosedJournals(carId, gosNum, sts);
    }

    @GetMapping("/{journalId}/services")
    public List<GetServiceDTO> getServicesFromJournal(@PathVariable Long journalId) {
        return journalService.getServicesFromJournal(journalId);
    }

    @PostMapping()
    public JournalDTO createJournal(@Valid @RequestBody JournalDTO journal) {
        return journalService.createJournal(journal);
    }

    @PutMapping("/{journalId}")
    public JournalDTO updateJournal(@Valid @RequestBody(required = false) JournalDTO journal,
                                    @PathVariable Long journalId) {
        return journalService.updateJournal(journal, journalId);
    }

    @PutMapping("/departure/{journalId}")
    public JournalDTO updateJournalAsDeparture(@PathVariable Long journalId) {
        return journalService.updateJournalAsDeparture(journalId);
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
}
