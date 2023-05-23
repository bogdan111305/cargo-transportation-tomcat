package com.example.cargo_transportation.controller;

import com.example.cargo_transportation.modal.dto.ServiceRequest;
import com.example.cargo_transportation.modal.dto.ServiceResponse;
import com.example.cargo_transportation.service.ServiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service")
public class ServiceRestController {
    private final ServiceService serviceService;

    @Autowired
    public ServiceRestController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping("/services")
    public List<ServiceResponse> getAllServices() {
        return serviceService.getAllServices();
    }

    @GetMapping("/{serviceId}")
    public ServiceResponse getServiceById(@PathVariable Long serviceId) {
        return serviceService.getServiceById(serviceId);
    }

    @PostMapping()
    public ServiceResponse createService(@Valid @RequestBody ServiceRequest service) {
        return serviceService.createService(service);
    }

    @PutMapping("/{serviceId}")
    public ServiceResponse updateService(@Valid @RequestBody ServiceRequest service, @PathVariable Long serviceId) {
        return serviceService.updateService(service, serviceId);
    }

    @DeleteMapping("/{serviceId}")
    public void deleteService(@PathVariable Long serviceId) {
        serviceService.deleteService(serviceId);
    }
}
