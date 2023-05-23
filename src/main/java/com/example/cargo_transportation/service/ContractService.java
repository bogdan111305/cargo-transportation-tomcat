package com.example.cargo_transportation.service;

import com.example.cargo_transportation.modal.dto.ContractRequest;
import com.example.cargo_transportation.modal.dto.ContractResponse;
import com.example.cargo_transportation.modal.dto.PriceRequest;
import com.example.cargo_transportation.entity.Contract;

import java.util.List;

public interface ContractService {
    ContractResponse getContractById(Long contractId);

    List<ContractResponse> getAllContracts();

    ContractResponse createContract(ContractRequest contractRequest);

    ContractResponse updateContract(ContractRequest contractRequest, Long contractId);

    void deleteContract(Long contractId);

    void addPriceFromContract(Long contractId, Long serviceId, Integer count);

    void removePriceFromContract(Long contractId, Long serviceId);

    Contract findContractById(Long contractId);
}
