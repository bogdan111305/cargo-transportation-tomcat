package com.example.cargo_transportation.service;

import com.example.cargo_transportation.entity.enums.JournalStatus;
import com.example.cargo_transportation.modal.dto.JournalRequest;
import com.example.cargo_transportation.modal.dto.GetServiceDTO;
import com.example.cargo_transportation.entity.Journal;
import com.example.cargo_transportation.modal.dto.JournalResponse;

import java.util.List;

public interface JournalService {
    JournalResponse getJournalById(Long journalId);

    List<JournalResponse> getAllJournal();

    List<JournalResponse> getOpenJournals(Long carId, String gosNum, String sts);

    JournalResponse createJournal(JournalRequest journalRequest);

    JournalResponse updateJournal(JournalRequest journalRequest, Long journalId);

    JournalResponse updateJournalStatus(Long journalId, JournalStatus status);

    void deleteJournal(Long journalId);

    List<GetServiceDTO> getServicesFromJournal(Long journalId);

    List<GetServiceDTO> addServicesFromJournal(Long journalId, List<GetServiceDTO> services);

    void addServiceFromJournal(Long journalId, Long serviceId, Integer count);

    void removeServiceFromJournal(Long journalId, Long serviceId);

    Journal findJournalById(Long journalId);
}
