package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.modal.dto.ContractRequest;
import com.example.cargo_transportation.modal.dto.ContractResponse;
import com.example.cargo_transportation.modal.dto.PriceRequest;
import com.example.cargo_transportation.modal.mapper.ContractMapper;
import com.example.cargo_transportation.entity.Car;
import com.example.cargo_transportation.entity.Client;
import com.example.cargo_transportation.entity.Contract;
import com.example.cargo_transportation.exception.EntityNotFoundException;
import com.example.cargo_transportation.repo.ContractRepository;
import com.example.cargo_transportation.service.CarService;
import com.example.cargo_transportation.service.ClientService;
import com.example.cargo_transportation.service.ContractService;
import com.example.cargo_transportation.service.ServiceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;
    private final ClientService clientService;
    private final CarService carService;
    private final ServiceService serviceService;
    private final ContractMapper contractMapper;

    @Autowired
    public ContractServiceImpl(ContractRepository contractRepository, ClientService clientService,
                               CarService carService, ServiceService serviceService, ContractMapper contractMapper) {
        this.contractRepository = contractRepository;
        this.clientService = clientService;
        this.carService = carService;
        this.serviceService = serviceService;
        this.contractMapper = contractMapper;
    }

    @Override
    public List<ContractResponse> getAllContracts() {
        return contractRepository.findAll().stream()
                .map(contractMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ContractResponse getContractById(Long contractId) {
        return contractMapper.toDTO(findContractById(contractId));
    }

    @Override
    public ContractResponse createContract(ContractRequest contractRequest) {
        Car car = carService.findCarById(contractRequest.getCarId());
        Client client = clientService.findClientById(contractRequest.getClientId());

        Contract contract = contractMapper.toEntity(contractRequest);
        contract.setCar(car);
        contract.setClient(client);

        fillPricesFromContract(contract, contractRequest.getPrices());

        contract = contractRepository.save(contract);
        log.info("The contract: {} is created", contract.getId());

        return contractMapper.toDTO(contract);
    }

    @Override
    @Transactional
    public ContractResponse updateContract(ContractRequest contractRequest, Long contractId) {
        Contract contract = findContractById(contractId);

        contract.setStartDate(contractRequest.getStartDate());
        contract.setEndDate(contractRequest.getEndDate());

        if (!contract.getCar().getId().equals(contractRequest.getCarId())) {
            Car car = carService.findCarById(contractRequest.getCarId());
            contract.setCar(car);
        }

        if (!contract.getClient().getId().equals(contractRequest.getClientId())) {
            Client client = clientService.findClientById(contractRequest.getClientId());
            contract.setClient(client);
        }

        fillPricesFromContract(contract, contractRequest.getPrices());

        contract = contractRepository.save(contract);
        log.info("The contract: {} is updated", contract.getId());

        return contractMapper.toDTO(contract);
    }

    @Override
    public void deleteContract(Long contractId) {
        Contract contract = findContractById(contractId);

        contractRepository.delete(contract);
        log.info("The contract: {} is deleted", contract.getId());
    }

    private void fillPricesFromContract(Contract contract, List<PriceRequest> prices) {
        if (contract == null || prices == null || prices.isEmpty()) {
            return;
        }

        contract.getPrices().clear();

        Map<Long, Integer> pricesMap = prices.stream()
                .collect(Collectors.toMap(PriceRequest::getServiceId, PriceRequest::getCost));

        List<com.example.cargo_transportation.entity.Service> services = serviceService.findServicesById(new ArrayList<>(pricesMap.keySet()));
        services.forEach(service -> contract.addPrice(service, pricesMap.get(service.getId())));
    }

    @Override
    public void addPriceFromContract(Long contractId, Long serviceId, Integer count) {
        Contract contract = findContractById(contractId);
        com.example.cargo_transportation.entity.Service service = serviceService.findServiceById(serviceId);

        contract.addPrice(service, count);

        contractRepository.save(contract);
        log.info("The price for service: {} by contract: {} is saved", contract.getId(), service.getId());
    }

    @Override
    @Transactional
    public void removePriceFromContract(Long contractId, Long serviceId) {
        Contract contract = findContractById(contractId);
        com.example.cargo_transportation.entity.Service service = serviceService.findServiceById(serviceId);

        contract.removePrice(service);

        contractRepository.save(contract);
        log.info("The price for service: {} by contract: {} is deleted", contract.getId(), service.getId());
    }

    @Override
    public Contract findContractById(Long contractId) {
        return contractRepository.findById(contractId)
                .orElseThrow(() -> new EntityNotFoundException("Contract not found with id: " + contractId));
    }
}
