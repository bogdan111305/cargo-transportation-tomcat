package com.example.cargo_transportation.service;

import com.example.cargo_transportation.dto.ContractDTO;
import com.example.cargo_transportation.entity.Contract;

import java.util.List;

public interface ContractService {
    List<ContractDTO> getAllContract(List<Long> ids);

    ContractDTO getContractById(Long contractId);

    Contract findContractById(Long contractId);

    ContractDTO createContract(ContractDTO contractDTO);

    ContractDTO updateContract(ContractDTO contractDTO, Long contractId);

    void deleteContract(Long contractId);
}
