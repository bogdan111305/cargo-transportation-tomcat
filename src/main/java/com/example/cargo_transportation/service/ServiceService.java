package com.example.cargo_transportation.service;

import com.example.cargo_transportation.modal.dto.ServiceRequest;
import com.example.cargo_transportation.entity.Service;
import com.example.cargo_transportation.modal.dto.ServiceResponse;

import java.util.List;

public interface ServiceService {
    List<ServiceResponse> getAllServices();

    List<Service> findServicesById(List<Long> ids);

    ServiceResponse getServiceById(Long serviceId);

    Service findServiceById(Long serviceId);

    ServiceResponse createService(ServiceRequest serviceRequest);

    ServiceResponse updateService(ServiceRequest serviceRequest, Long serviceId);

    void deleteService(Long serviceId);
}
