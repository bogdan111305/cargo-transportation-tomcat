package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.modal.dto.ServiceRequest;
import com.example.cargo_transportation.entity.Service;
import com.example.cargo_transportation.exception.EntityNotFoundException;
import com.example.cargo_transportation.modal.dto.ServiceResponse;
import com.example.cargo_transportation.modal.mapper.ServiceMapper;
import com.example.cargo_transportation.repo.ServiceRepository;
import com.example.cargo_transportation.service.ServiceService;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Log4j2
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    public ServiceServiceImpl(ServiceRepository serviceRepository, ServiceMapper serviceMapper) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
    }

    @Override
    public List<ServiceResponse> getAllService() {
        List<Service> services = serviceRepository.findAll();

        return services.stream()
                .map(serviceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Service> findServicesById(List<Long> ids) {
        return serviceRepository.findAllById(ids);
    }

    @Override
    public ServiceResponse getServiceById(Long serviceId) {
        return serviceMapper.toDTO(findServiceById(serviceId));
    }

    @Override
    public Service findServiceById(Long serviceId) {
        return serviceRepository.findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("Service not found with id: " + serviceId));
    }

    @Override
    public ServiceResponse createService(ServiceRequest serviceRequest) {
        Service service = serviceMapper.toEntity(serviceRequest);

        service = serviceRepository.save(service);
        log.info("The service: {} is saved", service.getName());

        return serviceMapper.toDTO(service);
    }

    @Override
    public ServiceResponse updateService(ServiceRequest serviceRequest, Long serviceId) {
        Service service = findServiceById(serviceId);

        service.setName(serviceRequest.getName());
        service.setDescription(serviceRequest.getDescription());

        service = serviceRepository.save(service);
        log.info("The service: {} is updated", service.getName());

        return serviceMapper.toDTO(service);
    }

    @Override
    public void deleteService(Long serviceId) {
        Service service = findServiceById(serviceId);

        serviceRepository.delete(service);
        log.info("The service: {} is saved", service.getName());
    }
}
