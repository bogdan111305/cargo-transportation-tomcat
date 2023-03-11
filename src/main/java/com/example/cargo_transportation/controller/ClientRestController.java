package com.example.cargo_transportation.controller;

import com.example.cargo_transportation.dto.ClientDTO;
import com.example.cargo_transportation.entity.Client;
import com.example.cargo_transportation.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public List<ClientDTO> getAllClient(@RequestBody(required = false) List<Long> ids) {
        return clientService.getAllClient(ids);
    }

    @GetMapping("/{clientId}")
    public ClientDTO getClientById(@PathVariable Long clientId) {
        return clientService.getClientById(clientId);
    }

    @PostMapping
    public ClientDTO createClient(@Valid @RequestBody ClientDTO client) {
        return clientService.createClient(client);
    }

    @PutMapping("/{clientId}")
    public ClientDTO updateClient(@Valid @RequestBody ClientDTO client, @PathVariable Long clientId) {
        return clientService.updateClient(client, clientId);
    }

    @DeleteMapping("/{clientId}")
    public void deleteClient(@PathVariable Long clientId) {
        clientService.deleteClient(clientId);
    }
}
