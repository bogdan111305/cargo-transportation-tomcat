package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.dto.FavorDTO;
import com.example.cargo_transportation.dto.JournalDTO;
import com.example.cargo_transportation.entity.Car;
import com.example.cargo_transportation.entity.Favor;
import com.example.cargo_transportation.entity.Journal;
import com.example.cargo_transportation.exception.EntityNotFoundException;
import com.example.cargo_transportation.repo.JournalRepository;
import com.example.cargo_transportation.service.CarService;
import com.example.cargo_transportation.service.FavorService;
import com.example.cargo_transportation.service.JournalService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
public class JournalServiceImpl implements JournalService {

    private final JournalRepository journalRepository;
    private final FavorService favorService;
    private final CarService carService;
    private final ModelMapper modelMapper;

    @Autowired
    public JournalServiceImpl(JournalRepository journalRepository, FavorService favorService, CarService carService, ModelMapper modelMapper) {
        this.journalRepository = journalRepository;
        this.favorService = favorService;
        this.carService = carService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<JournalDTO> getAllJournal() {
        return journalRepository.findAll().stream()
                .map(journal -> modelMapper.map(journal, JournalDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<JournalDTO> getJournalsByIds(List<Long> ids) {
        return journalRepository.findAllById(ids).stream()
                .map(journal -> modelMapper.map(journal, JournalDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Journal getJournalById(Long journalId) {
        return journalRepository.findById(journalId)
                .orElseThrow(() -> new EntityNotFoundException("Journal not found with id: " + journalId));
    }

    @Override
    public JournalDTO createJournal(JournalDTO journalDTO) {
        Journal journal = modelMapper.map(journalDTO, Journal.class);

        Car car = carService.getCarById(journalDTO.getCarId());
        journal.setCar(car);

        journal = journalRepository.save(journal);
        log.info("The favor: {} is saved" + journal.getId());

        return modelMapper.map(journal, JournalDTO.class);
    }

    @Override
    public JournalDTO updateJournal(JournalDTO journalDTO) {
        Journal journal = getJournalById(journalDTO.getId());

        journal.setIncomingDate(journalDTO.getIncomingDate());
        journal.setOutPlanDate(journalDTO.getOutPlanDate());
        journal.setOutFactDate(journalDTO.getOutFactDate());

        if (!journal.getId().equals(journalDTO.getCarId())) {
            Car car = carService.getCarById(journalDTO.getCarId());
            journal.setCar(car);
        }

        journal = journalRepository.save(journal);
        log.info("The favor: {} is updated" + journal.getId());

        return modelMapper.map(journal, JournalDTO.class);
    }

    @Override
    public void deleteJournal(Long journalId) {
        Journal journal = getJournalById(journalId);

        journalRepository.delete(journal);
        log.info("The journal: {} is saved" + journal.getId());
    }

    @Override
    public List<FavorDTO> addFavorsFromJournal(Long journalId, Map<Long, Integer> favors) {
        Journal journal = getJournalById(journalId);

        Journal finalJournal = journal;
        List<Favor> receivedFavors = favorService.getFavorsByIds(favors.keySet().stream().toList());
        receivedFavors.stream()
                .forEach(favor -> {
                    Integer count = favors.entrySet().stream()
                            .filter(f -> f.getKey().equals(favor.getId()))
                            .findFirst().get()
                            .getValue();
                    finalJournal.addFavor(favor, count);
                });

        journal = journalRepository.save(journal);
        log.info("The favor by journal: {} is updated" + journal.getId());

        return receivedFavors.stream()
                .map(f -> modelMapper.map(f, FavorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public FavorDTO addFavorFromJournal(Long journalId, Long favorId, Integer count) {
        Journal journal = getJournalById(journalId);
        Favor favor = favorService.getFavorById(favorId);

        journal.addFavor(favor, count);
        journalRepository.save(journal);

        return modelMapper.map(favor, FavorDTO.class);
    }

    public FavorDTO updateFavorFromJournal(Long journalId, Long favorId, Integer count) {
        Journal journal = getJournalById(journalId);
        Favor favor = favorService.getFavorById(favorId);

        journal.removeFavor(favor);
        journal.addFavor(favor, count);

        journalRepository.save(journal);

        return modelMapper.map(favor, FavorDTO.class);
    }

    @Override
    public void removeFavorFromJournal(Long journalId, Long favorId) {
        Journal journal = getJournalById(journalId);
        Favor favor = favorService.getFavorById(favorId);

        journal.removeFavor(favor);
        journalRepository.save(journal);
    }
}
