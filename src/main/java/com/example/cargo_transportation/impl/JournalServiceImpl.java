package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.entity.enums.JournalStatus;
import com.example.cargo_transportation.modal.dto.JournalRequest;
import com.example.cargo_transportation.modal.dto.GetServiceDTO;
import com.example.cargo_transportation.entity.Car;
import com.example.cargo_transportation.entity.Service;
import com.example.cargo_transportation.entity.Journal;
import com.example.cargo_transportation.exception.EntityNotFoundException;
import com.example.cargo_transportation.modal.dto.JournalResponse;
import com.example.cargo_transportation.modal.mapper.JournalMapper;
import com.example.cargo_transportation.repo.JournalRepository;
import com.example.cargo_transportation.service.CarService;
import com.example.cargo_transportation.service.ServiceService;
import com.example.cargo_transportation.service.JournalService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Log4j2
public class JournalServiceImpl implements JournalService {
    private final JournalRepository journalRepository;
    private final ServiceService serviceService;
    private final CarService carService;
    private final JournalMapper journalMapper;

    @Autowired
    public JournalServiceImpl(JournalRepository journalRepository, ServiceService serviceService,
                              CarService carService, JournalMapper journalMapper) {
        this.journalRepository = journalRepository;
        this.serviceService = serviceService;
        this.carService = carService;
        this.journalMapper = journalMapper;
    }

    @Override
    public JournalResponse getJournalById(Long journalId) {
        return journalMapper.toDTO(findJournalById(journalId));
    }

    @Override
    public List<JournalResponse> getAllJournal() {
        List<Journal> journals = journalRepository.findAll();
        return journals.stream()
                .map(journalMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<JournalResponse> getOpenJournals(Long carId, String gosNum, String sts) {
        List<Journal> journals = journalRepository.findOpenJournals(carId, gosNum, sts);
        return journals.stream()
                .map(journalMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<GetServiceDTO> getServicesFromJournal(Long journalId) {
        return findJournalById(journalId).getGetServices().stream()
                .map(GetServiceDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public JournalResponse createJournal(JournalRequest journalRequest) {
        Journal journal = journalMapper.toEntity(journalRequest);

        Car car = carService.findCarById(journalRequest.getCarId());
        journal.setCar(car);
        journal.setStatus(JournalStatus.OPEN);

        journal = journalRepository.save(journal);
        log.info("The journal: {} is saved", journal.getId());

        return journalMapper.toDTO(journal);
    }

    @Override
    public JournalResponse updateJournal(JournalRequest journalRequest, Long journalId) {
        Journal journal = findJournalById(journalId);

        journal.setIncomingDate(journalRequest.getIncomingDate());
        journal.setOutDate(journalRequest.getOutDate());
        journal.setWaybill(journalRequest.getWaybill());
        journal.setNameDriver(journalRequest.getNameDriver());

        if (journalRequest.getStatus() != null) {
            journal.setStatus(journalRequest.getStatus());
        }

        if (!journal.getId().equals(journalRequest.getCarId())) {
            Car car = carService.findCarById(journalRequest.getCarId());
            journal.setCar(car);
        }

        journal = journalRepository.save(journal);
        log.info("The journal: {} is updated", journal.getId());

        return journalMapper.toDTO(journal);
    }

    @Override
    public JournalResponse updateJournalStatus(Long journalId, JournalStatus status) {
        Journal journal = findJournalById(journalId);

        journal.setStatus(status);

        journal = journalRepository.save(journal);
        log.info("The journal: {} is updated as departure", journal.getId());

        return journalMapper.toDTO(journal);
    }

    @Override
    public void deleteJournal(Long journalId) {
        Journal journal = findJournalById(journalId);

        journalRepository.delete(journal);
        log.info("The journal: {} is saved", journal.getId());
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
                .map(GetServiceDTO::new)
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
    public Journal findJournalById(Long journalId) {
        return journalRepository.findById(journalId)
                .orElseThrow(() -> new EntityNotFoundException("Journal not found with id: " + journalId));
    }
}
