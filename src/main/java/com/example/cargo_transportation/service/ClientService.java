package com.example.cargo_transportation.service;


import com.example.cargo_transportation.modal.dto.ClientRequest;
import com.example.cargo_transportation.entity.Client;
import com.example.cargo_transportation.modal.dto.ClientResponse;

import java.util.List;

public interface ClientService {
    ClientResponse getClientById(Long clientId);

    List<ClientResponse> getAllClients();

    ClientResponse createClient(ClientRequest clientRequest);

    ClientResponse updateClient(ClientRequest clientRequest, Long clientId);

    void deleteClient(Long clientId);

    Client findClientById(Long clientId);
}
