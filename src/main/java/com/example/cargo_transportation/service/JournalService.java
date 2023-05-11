package com.example.cargo_transportation.service;

import com.example.cargo_transportation.entity.enums.JournalStatus;
import com.example.cargo_transportation.modal.dto.JournalRequest;
import com.example.cargo_transportation.modal.dto.ProvideServiceRequest;
import com.example.cargo_transportation.entity.Journal;
import com.example.cargo_transportation.modal.dto.JournalResponse;
import com.example.cargo_transportation.modal.dto.ProvideServiceResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface JournalService {
    JournalResponse getJournalById(Long journalId);

    List<JournalResponse> getJournals(JournalStatus status, String gosNum,
                                      String sts, Long clientId,
                                      LocalDateTime startDateReport,
                                      LocalDateTime endDateReport);

    JournalResponse createJournal(JournalRequest journalRequest);

    JournalResponse updateJournal(JournalRequest journalRequest, Long journalId);

    void updateJournalStatus(Long journalId, JournalStatus status);

    void deleteJournal(Long journalId);

    void addServiceFromJournal(Long journalId, Long serviceId, Integer count);

    void removeServiceFromJournal(Long journalId, Long serviceId);

    Journal findJournalById(Long journalId);

    List<JournalStatus> getJournalStatuses();
}
