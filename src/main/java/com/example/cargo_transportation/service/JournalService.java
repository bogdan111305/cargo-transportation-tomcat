package com.example.cargo_transportation.service;

import com.example.cargo_transportation.dto.FavorDTO;
import com.example.cargo_transportation.dto.JournalDTO;
import com.example.cargo_transportation.entity.Journal;

import java.util.List;
import java.util.Map;

public interface JournalService {
    List<JournalDTO> getAllJournal();

    List<JournalDTO> getJournalsByIds(List<Long> ids);

    Journal getJournalById(Long journalId);

    JournalDTO createJournal(JournalDTO journalDTO);

    JournalDTO updateJournal(JournalDTO journalDTO);

    void deleteJournal(Long journalId);

    List<FavorDTO> addFavorsFromJournal(Long journalId, Map<Long, Integer> favors);

    FavorDTO addFavorFromJournal(Long journalId, Long favorId, Integer count);

    void removeFavorFromJournal(Long journalId, Long favorId);
}
