package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.dto.ContractDTO;
import com.example.cargo_transportation.entity.Car;
import com.example.cargo_transportation.entity.Client;
import com.example.cargo_transportation.entity.Contract;
import com.example.cargo_transportation.exception.EntityNotFoundException;
import com.example.cargo_transportation.repo.ContractRepository;
import com.example.cargo_transportation.service.CarService;
import com.example.cargo_transportation.service.ClientService;
import com.example.cargo_transportation.service.ContractService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final ClientService clientService;
    private final CarService carService;
    private final ModelMapper modelMapper;

    @Autowired
    public ContractServiceImpl(ContractRepository contractRepository, ClientService clientService,
                               CarService carService, ModelMapper modelMapper) {
        this.contractRepository = contractRepository;
        this.clientService = clientService;
        this.carService = carService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ContractDTO> getAllContract(List<Long> ids) {
        List<Contract> contracts = null;
        if (ids != null && !ids.isEmpty())
            contracts = contractRepository.findAllById(ids);
        else
            contracts = contractRepository.findAll();
        return contracts.stream()
                .map(contract -> modelMapper.map(contract, ContractDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ContractDTO getContractById(Long contractId) {
        return modelMapper.map(findContractById(contractId), ContractDTO.class);
    }

    @Override
    public Contract findContractById(Long contractId) {
        return contractRepository.findById(contractId)
                .orElseThrow(() -> new EntityNotFoundException("Contract not found with id: " + contractId));
    }

    @Override
    public ContractDTO createContract(ContractDTO contractDTO) {
        Car car = carService.findCarById(contractDTO.getCarId());
        Client client = clientService.findClientById(contractDTO.getClientId());

        Contract contract = modelMapper.map(contractDTO, Contract.class);
        contract.setCar(car);
        contract.setClient(client);

        contract = contractRepository.save(contract);
        log.info("The contract: {} is created" + contract.getId());

        return modelMapper.map(contract, ContractDTO.class);
    }

    @Override
    public ContractDTO updateContract(ContractDTO contractDTO, Long contractId) {
        Contract contract = findContractById(contractId);

        contract.setStartDate(contractDTO.getStartDate());
        contract.setEndDate(contractDTO.getEndDate());

        if (!contract.getCar().getId().equals(contractDTO.getCarId())) {
            Car car = carService.findCarById(contractDTO.getCarId());
            contract.setCar(car);
        }

        if (!contract.getClient().getId().equals(contractDTO.getClientId())) {
            Client client = clientService.findClientById(contractDTO.getClientId());
            contract.setClient(client);
        }

        contract = contractRepository.save(contract);
        log.info("The contract: {} is updated" + contract.getId());

        return modelMapper.map(contract, ContractDTO.class);
    }

    @Override
    public void deleteContract(Long contractId) {
        Contract contract = findContractById(contractId);

        contractRepository.delete(contract);
        log.info("The contract: {} is deleted" + contract.getId());
    }
}
