package com.example.cargo_transportation.service;

import com.example.cargo_transportation.dto.ServiceDTO;
import com.example.cargo_transportation.entity.Service;

import java.util.List;

public interface ServiceService {
    List<ServiceDTO> getAllService(List<Long> ids);

    List<Service> findServicesById(List<Long> ids);

    ServiceDTO getServiceById(Long serviceId);

    Service findServiceById(Long serviceId);

    ServiceDTO createService(ServiceDTO serviceDTO);

    ServiceDTO updateService(ServiceDTO serviceDTO, Long serviceId);

    void deleteService(Long serviceId);
}
