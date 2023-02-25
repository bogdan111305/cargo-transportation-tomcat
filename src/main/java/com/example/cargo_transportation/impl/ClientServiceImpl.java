package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.dto.ClientDTO;
import com.example.cargo_transportation.entity.Client;
import com.example.cargo_transportation.exception.EntityNotFoundException;
import com.example.cargo_transportation.repo.ClientRepository;
import com.example.cargo_transportation.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.cargo_transportation.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ClientDTO> getAllClient(List<Long> ids) {
        List<Client> clients = null;
        if (ids != null && !ids.isEmpty())
            clients = clientRepository.findAllById(ids);
        else
            clients = clientRepository.findAll();

        return clients.stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClientById(Long clientId) {
        return modelMapper.map(findClientById(clientId), ClientDTO.class);
    }

    @Override
    public Client findClientById(Long clientId) {
        return clientRepository.findClientById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + clientId));
    }

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = modelMapper.map(clientDTO, Client.class);

        try {
            client = clientRepository.save(client);
            log.info("The client: {} is saved" + client.getName());
        } catch (ConstraintViolationException e) {
            log.error(e.getMessage());
        }


        return modelMapper.map(client, ClientDTO.class);
    }

    @Override
    public ClientDTO updateClient(ClientDTO clientDTO, Long clientId) {
        Client client = findClientById(clientId);

        client.setName(clientDTO.getName());
        client.setAddress(clientDTO.getAddress());
        client.setInn(clientDTO.getInn());
        client.setKpp(clientDTO.getKpp());
        client.setRs(clientDTO.getRs());
        client.setBank(clientDTO.getBank());
        client.setBik(clientDTO.getBik());
        client.setKs(clientDTO.getKs());

        client = clientRepository.save(client);
        log.info("The client: {} is updated" + client.getName());

        return modelMapper.map(client, ClientDTO.class);
    }

    @Override
    public void deleteClient(Long clientId) {
        Client client = findClientById(clientId);

        clientRepository.delete(client);
        log.info("The client: {} is saved" + client.getName());
    }
}
