package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.dto.ServiceDTO;
import com.example.cargo_transportation.entity.Service;
import com.example.cargo_transportation.exception.EntityNotFoundException;
import com.example.cargo_transportation.repo.ServiceRepository;
import com.example.cargo_transportation.service.ServiceService;
import lombok.extern.log4j.Log4j2;
import com.example.cargo_transportation.dto.mapper.CustomMapper;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Log4j2
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final CustomMapper customMapper;

    public ServiceServiceImpl(ServiceRepository serviceRepository, CustomMapper customMapper) {
        this.serviceRepository = serviceRepository;
        this.customMapper = customMapper;
    }

    @Override
    public List<ServiceDTO> getAllService(List<Long> ids) {
        List<Service> services;
        if (ids != null && !ids.isEmpty())
            services = findServicesById(ids);
        else
            services = serviceRepository.findAll();
        return services.stream()
                .map(service -> customMapper.mapToDTOWithSpecificFields(service, ServiceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Service> findServicesById(List<Long> ids) {
        return serviceRepository.findAllById(ids);
    }

    @Override
    public ServiceDTO getServiceById(Long serviceId) {
        return customMapper.mapToDTOWithSpecificFields(findServiceById(serviceId), ServiceDTO.class);
    }

    @Override
    public Service findServiceById(Long serviceId) {
        return serviceRepository.findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("Service not found with id: " + serviceId));
    }

    @Override
    public ServiceDTO createService(ServiceDTO serviceDTO) {
        Service service = customMapper.defaultMap(serviceDTO, Service.class);

        service = serviceRepository.save(service);
        log.info("The service: {} is saved", service.getName());

        return customMapper.mapToDTOWithSpecificFields(service, ServiceDTO.class);
    }

    @Override
    public ServiceDTO updateService(ServiceDTO serviceDTO, Long serviceId) {
        Service service = findServiceById(serviceId);

        service.setName(serviceDTO.getName());
        service.setDescription(serviceDTO.getDescription());

        service = serviceRepository.save(service);
        log.info("The service: {} is updated", service.getName());

        return customMapper.mapToDTOWithSpecificFields(service, ServiceDTO.class);
    }

    @Override
    public void deleteService(Long serviceId) {
        Service service = findServiceById(serviceId);

        serviceRepository.delete(service);
        log.info("The service: {} is saved", service.getName());
    }
}
