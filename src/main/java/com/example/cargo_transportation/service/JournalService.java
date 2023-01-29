package com.example.cargo_transportation.service;

import com.example.cargo_transportation.dto.JournalDTO;
import com.example.cargo_transportation.entity.Journal;

import java.util.List;

public interface JournalService {
    List<JournalDTO> getAllJournal();

    List<JournalDTO> getJournalsByIds(List<Integer> ids);

    Journal getJournalById(Long journalId);

    JournalDTO createJournal(JournalDTO journalDTO);

    JournalDTO updateJournal(JournalDTO journalDTO);

    void deleteJournal(Long journalId);
}
