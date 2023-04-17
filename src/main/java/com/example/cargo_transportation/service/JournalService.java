package com.example.cargo_transportation.service;

import com.example.cargo_transportation.entity.enums.JournalStatus;
import com.example.cargo_transportation.modal.dto.JournalDTO;
import com.example.cargo_transportation.modal.dto.GetServiceDTO;
import com.example.cargo_transportation.entity.Journal;

import java.util.List;

public interface JournalService {
    JournalDTO getJournalById(Long journalId);

    List<JournalDTO> getAllJournal();

    List<JournalDTO> getOpenJournals(Long carId, String gosNum, String sts);

    JournalDTO createJournal(JournalDTO journalDTO);

    JournalDTO updateJournal(JournalDTO journalDTO, Long journalId);

    JournalDTO updateJournalStatus(Long journalId, JournalStatus status);

    void deleteJournal(Long journalId);

    List<GetServiceDTO> getServicesFromJournal(Long journalId);

    List<GetServiceDTO> addServicesFromJournal(Long journalId, List<GetServiceDTO> services);

    void addServiceFromJournal(Long journalId, Long serviceId, Integer count);

    void removeServiceFromJournal(Long journalId, Long serviceId);

    Journal findJournalById(Long journalId);
}
