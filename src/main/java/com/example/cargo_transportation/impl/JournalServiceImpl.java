package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.modal.dto.JournalDTO;
import com.example.cargo_transportation.modal.dto.GetServiceDTO;
import com.example.cargo_transportation.entity.Car;
import com.example.cargo_transportation.entity.Service;
import com.example.cargo_transportation.entity.Journal;
import com.example.cargo_transportation.exception.EntityNotFoundException;
import com.example.cargo_transportation.modal.report.JournalReport;
import com.example.cargo_transportation.repo.JournalRepository;
import com.example.cargo_transportation.service.CarService;
import com.example.cargo_transportation.service.ServiceService;
import com.example.cargo_transportation.service.JournalService;
import lombok.extern.log4j.Log4j2;
import com.example.cargo_transportation.modal.mapper.CustomMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Log4j2
public class JournalServiceImpl implements JournalService {
    private final JournalRepository journalRepository;
    private final ServiceService serviceService;
    private final CarService carService;
    private final CustomMapper customMapper;

    @Autowired
    public JournalServiceImpl(JournalRepository journalRepository, ServiceService serviceService,
                              CarService carService, CustomMapper customMapper) {
        this.journalRepository = journalRepository;
        this.serviceService = serviceService;
        this.carService = carService;
        this.customMapper = customMapper;
    }

    @Override
    public List<JournalDTO> getAllJournal(List<Long> ids) {
        List<Journal> journals;
        if (ids != null && !ids.isEmpty())
            journals = journalRepository.findAllById(ids);
        else
            journals = journalRepository.findAll();
        return journals.stream()
                .map(journal -> customMapper.mapToDTOWithSpecificFields(journal, JournalDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public JournalDTO getJournalById(Long journalId) {
        return customMapper.mapToDTOWithSpecificFields(findJournalById(journalId), JournalDTO.class);
    }

    @Override
    public Journal findJournalById(Long journalId) {
        return journalRepository.findById(journalId)
                .orElseThrow(() -> new EntityNotFoundException("Journal not found with id: " + journalId));
    }

    @Override
    public JournalDTO createJournal(JournalDTO journalDTO) {
        Journal journal = customMapper.defaultMap(journalDTO, Journal.class);

        Car car = carService.findCarById(journalDTO.getCarId());
        journal.setCar(car);

        journal = journalRepository.save(journal);
        log.info("The journal: {} is saved", journal.getId());

        return customMapper.mapToDTOWithSpecificFields(journal, JournalDTO.class);
    }

    @Override
    public JournalDTO updateJournal(JournalDTO journalDTO, Long journalId) {
        Journal journal = findJournalById(journalId);

        journal.setIncomingDate(journalDTO.getIncomingDate());
        journal.setOutPlanDate(journalDTO.getOutPlanDate());
        journal.setOutFactDate(journalDTO.getOutFactDate());
        journal.setWaybill(journalDTO.getWaybill());
        journal.setNameDriver(journalDTO.getNameDriver());

        if (!journal.getId().equals(journalDTO.getCarId())) {
            Car car = carService.findCarById(journalDTO.getCarId());
            journal.setCar(car);
        }

        journal = journalRepository.save(journal);
        log.info("The journal: {} is updated", journal.getId());

        return customMapper.mapToDTOWithSpecificFields(journal, JournalDTO.class);
    }

    @Override
    public void deleteJournal(Long journalId) {
        Journal journal = findJournalById(journalId);

        journalRepository.delete(journal);
        log.info("The journal: {} is saved", journal.getId());
    }

    @Override
    public List<GetServiceDTO> getServicesFromJournal(Long journalId) {
        return findJournalById(journalId).getGetServices().stream()
                .map(rf -> new GetServiceDTO(rf.getService().getId(), rf.getCount()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GetServiceDTO> addServicesFromJournal(Long journalId, List<GetServiceDTO> services) {
        Journal journal = findJournalById(journalId);

        Journal finalJournal = journal;
        List<Long> servicesId = services.stream()
                .map(s -> s.getServiceId())
                .collect(Collectors.toList());
        serviceService.findServicesById(servicesId)
                .forEach(service -> {
                    Integer count = services.stream()
                            .filter(f -> f.getServiceId().equals(service.getId()))
                            .findFirst().get()
                            .getCount();
                    finalJournal.addService(service, count);
                });

        journal = journalRepository.save(finalJournal);
        log.info("The services by journal: {} is saved", journalId);

        return journal.getGetServices().stream()
                .map(rf -> new GetServiceDTO(rf.getService().getId(), rf.getCount()))
                .collect(Collectors.toList());
    }

    @Override
    public void addServiceFromJournal(Long journalId, Long serviceId, Integer count) {
        Journal journal = findJournalById(journalId);
        Service service = serviceService.findServiceById(serviceId);

        journal.addService(service, count);

        journalRepository.save(journal);
        log.info("The service: {} by journal: {} is saved", journalId, serviceId);
    }

    @Override
    public void removeServiceFromJournal(Long journalId, Long serviceId) {
        Journal journal = findJournalById(journalId);
        Service service = serviceService.findServiceById(serviceId);

        journal.removeService(service);

        journalRepository.save(journal);
        log.info("The service: {} by journal: {} is deleted", journalId, serviceId);
    }

    @Override
    public List<JournalReport> getJournalReport(String gosNum) {
        return journalRepository.getJournalReport(gosNum);
    }
}
