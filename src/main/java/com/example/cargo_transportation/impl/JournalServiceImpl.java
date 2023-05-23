package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.entity.ProvideService;
import com.example.cargo_transportation.entity.enums.JournalStatus;
import com.example.cargo_transportation.modal.dto.JournalRequest;
import com.example.cargo_transportation.modal.dto.ProvideServiceRequest;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.cargo_transportation.common.Common.DEFAULT_END_DATE;
import static com.example.cargo_transportation.common.Common.DEFAULT_START_DATE;

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
    public List<JournalStatus> getJournalStatuses() {
        return List.of(JournalStatus.values());
    }

    @Override
    @Transactional
    public JournalResponse getJournalById(Long journalId) {
        return journalMapper.toDTO(findJournalById(journalId));
    }

    @Override
    public List<JournalResponse> getJournals(JournalStatus status, String gosNum, String sts, Long clientId,
                                             LocalDateTime startDateReport, LocalDateTime endDateReport) {
        startDateReport = startDateReport != null ? startDateReport : DEFAULT_START_DATE;
        endDateReport = endDateReport != null ? endDateReport : DEFAULT_END_DATE;

        List<Journal> journals = journalRepository.findJournalsByFilters(status, gosNum, sts, clientId, startDateReport, endDateReport);

        return journals.stream()
                .map(journalMapper::toDTOWithClient)
                .collect(Collectors.toList());
    }

    @Override
    public JournalResponse createJournal(JournalRequest journalRequest) {
        Journal journal = journalMapper.toEntity(journalRequest);
        Car car = carService.findCarById(journalRequest.getCarId());

        journal.setCar(car);
        journal.setStatus(JournalStatus.OPEN);

        fillServicesFromJournal(journal, journalRequest.getProvideServices());

        journal = journalRepository.save(journal);
        log.info("The journal: {} is saved", journal.getId());

        return journalMapper.toDTO(journal);
    }

    @Override
    @Transactional
    public JournalResponse updateJournal(JournalRequest journalRequest, Long journalId) {
        Journal journal = findJournalById(journalId);
        updateJournalFields(journal, journalRequest);

        journal = journalRepository.save(journal);
        log.info("The journal: {} is updated", journal.getId());

        return journalMapper.toDTO(journal);
    }

    private void updateJournalFields(Journal journal, JournalRequest journalRequest) {
        journal.setIncomingDate(journalRequest.getIncomingDate());
        journal.setOutDate(journalRequest.getOutDate());
        journal.setWaybill(journalRequest.getWaybill());
        journal.setNameDriver(journalRequest.getNameDriver());

        if (journalRequest.getStatus() != null) {
            journal.setStatus(journalRequest.getStatus());
        }

        fillServicesFromJournal(journal, journalRequest.getProvideServices());
    }

    private void fillServicesFromJournal(Journal journal, List<ProvideServiceRequest> provideServices) {
        if (journal == null || provideServices == null || provideServices.isEmpty()) {
            return;
        }

        journal.getProvideServices().clear();

        Map<Long, Integer> servicesMap = provideServices.stream()
                .collect(Collectors.toMap(ProvideServiceRequest::getServiceId, ProvideServiceRequest::getCount));

        List<Service> services = serviceService.findServicesById(new ArrayList<>(servicesMap.keySet()));
        services.forEach(service -> journal.addService(service, servicesMap.get(service.getId())));
    }

    @Override
    public void updateJournalStatus(Long journalId, JournalStatus status) {
        Journal journal = findJournalById(journalId);

        journal.setStatus(status);

        journal = journalRepository.save(journal);
        log.info("The journal: {} is updated as departure", journal.getId());
    }

    @Override
    public void deleteJournal(Long journalId) {
        Journal journal = findJournalById(journalId);

        journalRepository.delete(journal);
        log.info("The journal: {} is saved", journal.getId());
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
