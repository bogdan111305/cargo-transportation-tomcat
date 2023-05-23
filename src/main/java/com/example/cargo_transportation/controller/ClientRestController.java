package com.example.cargo_transportation.controller;

import com.example.cargo_transportation.modal.dto.ClientRequest;
import com.example.cargo_transportation.modal.dto.ClientResponse;
import com.example.cargo_transportation.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientRestController {
    private final ClientService clientService;

    @Autowired
    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients")
    public List<ClientResponse> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{clientId}")
    public ClientResponse getClientById(@PathVariable Long clientId) {
        return clientService.getClientById(clientId);
    }

    @PostMapping()
    public ClientResponse createClient(@Valid @RequestBody ClientRequest clientRequest) {
        return clientService.createClient(clientRequest);
    }

    @PutMapping("/{clientId}")
    public ClientResponse updateClient(@Valid @RequestBody ClientRequest clientRequest, @PathVariable Long clientId) {
        return clientService.updateClient(clientRequest, clientId);
    }

    @DeleteMapping("/{clientId}")
    public void deleteClient(@PathVariable Long clientId) {
        clientService.deleteClient(clientId);
    }
}
