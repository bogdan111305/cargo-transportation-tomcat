package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.modal.dto.ServiceDTO;
import com.example.cargo_transportation.entity.Service;
import com.example.cargo_transportation.exception.EntityNotFoundException;
import com.example.cargo_transportation.repo.ServiceRepository;
import com.example.cargo_transportation.service.ServiceService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Log4j2
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;
    private final ModelMapper modelMapper;

    public ServiceServiceImpl(ServiceRepository serviceRepository, ModelMapper modelMapper) {
        this.serviceRepository = serviceRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ServiceDTO> getAllService() {
        List<Service> services = serviceRepository.findAll();

        return services.stream()
                .map(service -> modelMapper.map(service, ServiceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Service> findServicesById(List<Long> ids) {
        return serviceRepository.findAllById(ids);
    }

    @Override
    public ServiceDTO getServiceById(Long serviceId) {
        return modelMapper.map(findServiceById(serviceId), ServiceDTO.class);
    }

    @Override
    public Service findServiceById(Long serviceId) {
        return serviceRepository.findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("Service not found with id: " + serviceId));
    }

    @Override
    public ServiceDTO createService(ServiceDTO serviceDTO) {
        Service service = modelMapper.map(serviceDTO, Service.class);

        service = serviceRepository.save(service);
        log.info("The service: {} is saved", service.getName());

        return modelMapper.map(service, ServiceDTO.class);
    }

    @Override
    public ServiceDTO updateService(ServiceDTO serviceDTO, Long serviceId) {
        Service service = findServiceById(serviceId);

        service.setName(serviceDTO.getName());
        service.setDescription(serviceDTO.getDescription());

        service = serviceRepository.save(service);
        log.info("The service: {} is updated", service.getName());

        return modelMapper.map(service, ServiceDTO.class);
    }

    @Override
    public void deleteService(Long serviceId) {
        Service service = findServiceById(serviceId);

        serviceRepository.delete(service);
        log.info("The service: {} is saved", service.getName());
    }
}
