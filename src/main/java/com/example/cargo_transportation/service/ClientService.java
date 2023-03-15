package com.example.cargo_transportation.service;


import com.example.cargo_transportation.modal.dto.ClientDTO;
import com.example.cargo_transportation.entity.Client;

import java.util.List;

public interface ClientService {
    List<ClientDTO> getAllClient(List<Long> ids);

    ClientDTO getClientById(Long clientId);

    Client findClientById(Long clientId);

    ClientDTO createClient(ClientDTO clientDTO);

    ClientDTO updateClient(ClientDTO clientDTO, Long clientId);

    void deleteClient(Long clientId);
}
