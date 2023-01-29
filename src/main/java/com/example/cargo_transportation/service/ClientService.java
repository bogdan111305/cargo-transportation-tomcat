package com.example.cargo_transportation.service;


import com.example.cargo_transportation.dto.ClientDTO;
import com.example.cargo_transportation.entity.Client;

import java.util.List;

public interface ClientService {
    List<ClientDTO> getAllClient();

    List<ClientDTO> getClientsByIds(List<Long> ids);

    Client getClientById(Long clientId);

    ClientDTO createClient(ClientDTO clientDTO);

    ClientDTO updateClient(ClientDTO clientDTO);

    void deleteClient(Long clientId);
}
