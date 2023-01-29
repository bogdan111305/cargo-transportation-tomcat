package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.dto.CarDTO;
import com.example.cargo_transportation.dto.ClientDTO;
import com.example.cargo_transportation.entity.Car;
import com.example.cargo_transportation.entity.Client;
import com.example.cargo_transportation.exception.EntityConversionException;
import com.example.cargo_transportation.exception.EntityNotFoundException;
import com.example.cargo_transportation.repo.ClientRepository;
import com.example.cargo_transportation.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<ClientDTO> getAllClient() {
        return clientRepository.findAll().stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientDTO> getClientsByIds(List<Long> ids) {
        return clientRepository.findAllById(ids).stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Client getClientById(Long clientId) {
        return clientRepository.findClientById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id" + clientId));
    }

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = modelMapper.map(clientDTO, Client.class);

        try {
            log.info("Saving Client.");
            client = clientRepository.save(client);
            log.info("The client was saved successfully.");
        }catch (Exception e) {
            log.error("Error during registration. {}" + e.getMessage());
            throw new EntityConversionException("Client registration error.");
        }

        return modelMapper.map(client, ClientDTO.class);
    }

    @Override
    public ClientDTO updateClient(ClientDTO clientDTO) {
        Client client = getClientById(clientDTO.getId());

        client.setName(clientDTO.getName());
        client.setAddress(clientDTO.getAddress());
        client.setEmail(clientDTO.getEmail());
        client.setINN(clientDTO.getINN());
        client.setKPP(clientDTO.getKPP());

        try {
            log.info("Updating Client.");
            client = clientRepository.save(client);
            log.info("The client was updated successfully.");
        }catch (Exception e) {
            log.error("Error during updating. {}" + e.getMessage());
            throw new EntityConversionException("Client updating error.");
        }

        return modelMapper.map(client, ClientDTO.class);
    }

    @Override
    public void deleteClient(Long clientId) {
        Client client = getClientById(clientId);

        try {
            log.info("Deleting Client");
            clientRepository.delete(client);
            log.info("The client was deleted successfully.");
        }catch (Exception e){
            log.error("Error during deleting. {}" + e.getMessage());
            throw new EntityConversionException("Client deleting error.");
        }
    }
}
