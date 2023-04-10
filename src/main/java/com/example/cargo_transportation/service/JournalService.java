package com.example.cargo_transportation.service;

import com.example.cargo_transportation.modal.dto.JournalDTO;
import com.example.cargo_transportation.modal.dto.GetServiceDTO;
import com.example.cargo_transportation.entity.Journal;
import com.example.cargo_transportation.modal.report.JournalReport;

import java.time.LocalDateTime;
import java.util.List;

public interface JournalService {
    List<JournalDTO> getAllJournal(List<Long> ids);

    JournalDTO getJournalById(Long journalId);

    Journal findJournalById(Long journalId);

    List<JournalDTO> getUnclosedJournalsByCarId(Long carId);

    JournalDTO createJournal(JournalDTO journalDTO);

    JournalDTO updateJournal(JournalDTO journalDTO, Long journalId);

    JournalDTO updateJournalAsDeparture(Long journalId);

    void deleteJournal(Long journalId);

    List<GetServiceDTO> getServicesFromJournal(Long journalId);

    List<GetServiceDTO> addServicesFromJournal(Long journalId, List<GetServiceDTO> services);

    void addServiceFromJournal(Long journalId, Long serviceId, Integer count);

    void removeServiceFromJournal(Long journalId, Long serviceId);
}
