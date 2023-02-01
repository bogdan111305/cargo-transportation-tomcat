package com.example.cargo_transportation.service;

import com.example.cargo_transportation.dto.ContractDTO;
import com.example.cargo_transportation.entity.Contract;

import java.util.List;

public interface ContractService {
    List<ContractDTO> getAllContract();

    List<ContractDTO> getContractsByIds(List<Long> ids);

    Contract getContractById(Long contractId);

    ContractDTO createContract(ContractDTO contractDTO);

    ContractDTO updateContract(ContractDTO contractDTO);

    void deleteContract(Long contractId);
}
