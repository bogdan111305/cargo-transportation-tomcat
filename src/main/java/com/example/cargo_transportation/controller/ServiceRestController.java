package com.example.cargo_transportation.controller;

import com.example.cargo_transportation.dto.ServiceDTO;
import com.example.cargo_transportation.service.ServiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service")
public class ServiceRestController {

    private final ServiceService serviceService;

    @Autowired
    public ServiceRestController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping("/services")
    public List<ServiceDTO> getAllService(@RequestBody(required = false) List<Long> ids) {
        return serviceService.getAllService(ids);
    }

    @GetMapping("/{serviceId}")
    public ServiceDTO getServiceById(@PathVariable Long serviceId) {
        return serviceService.getServiceById(serviceId);
    }

    @PostMapping()
    public ServiceDTO createService(@Valid @RequestBody ServiceDTO service) {
        return serviceService.createService(service);
    }

    @PutMapping("/{serviceId}")
    public ServiceDTO updateService(@Valid @RequestBody ServiceDTO service, @PathVariable Long serviceId) {
        return serviceService.updateService(service, serviceId);
    }

    @DeleteMapping("/{serviceId}")
    public void deleteService(@PathVariable Long serviceId) {
        serviceService.deleteService(serviceId);
    }
}
