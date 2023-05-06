package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.modal.dto.ClientRequest;
import com.example.cargo_transportation.entity.Client;
import com.example.cargo_transportation.exception.EntityNotFoundException;
import com.example.cargo_transportation.modal.dto.ClientResponse;
import com.example.cargo_transportation.modal.mapper.ClientMapper;
import com.example.cargo_transportation.repo.ClientRepository;
import com.example.cargo_transportation.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public List<ClientResponse> getAllClient() {
        List<Client> clients = clientRepository.findAll();

        return clients.stream()
                .map(clientMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClientResponse getClientById(Long clientId) {
        return clientMapper.toDTO(findClientById(clientId));
    }

    @Override
    public Client findClientById(Long clientId) {
        return clientRepository.findClientById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + clientId));
    }

    @Override
    public ClientResponse createClient(ClientRequest clientRequest) {
        Client client = clientMapper.toEntity(clientRequest);

        client = clientRepository.save(client);
        log.info("The client: {} is saved", client.getName());

        return clientMapper.toDTO(client);
    }

    @Override
    public ClientResponse updateClient(ClientRequest clientRequest, Long clientId) {
        Client client = findClientById(clientId);

        client.setName(clientRequest.getName());
        client.setAddress(clientRequest.getAddress());
        client.setInn(clientRequest.getInn());
        client.setKpp(clientRequest.getKpp());
        client.setRs(clientRequest.getRs());
        client.setBank(clientRequest.getBank());
        client.setBik(clientRequest.getBik());
        client.setKs(clientRequest.getKs());

        client = clientRepository.save(client);
        log.info("The client: {} is updated", client.getName());

        return clientMapper.toDTO(client);
    }

    @Override
    public void deleteClient(Long clientId) {
        Client client = findClientById(clientId);

        clientRepository.delete(client);
        log.info("The client: {} is saved", client.getName());
    }
}
