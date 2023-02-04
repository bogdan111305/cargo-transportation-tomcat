package com.example.cargo_transportation.service;

import com.example.cargo_transportation.dto.ContractDTO;
import com.example.cargo_transportation.dto.PriceDTO;
import com.example.cargo_transportation.entity.Contract;

import java.util.List;

public interface ContractService {
    List<ContractDTO> getAllContract(List<Long> ids);

    ContractDTO getContractById(Long contractId);

    Contract findContractById(Long contractId);

    ContractDTO createContract(ContractDTO contractDTO);

    ContractDTO updateContract(ContractDTO contractDTO, Long contractId);

    void deleteContract(Long contractId);

    List<PriceDTO> getPricesFromContract(Long contractId) ;

    List<PriceDTO> addPricesFromContract(Long contractId, List<PriceDTO> services);

    void addPriceFromContract(Long contractId, Long serviceId, Integer count);

    void removePriceFromContract(Long contractId, Long serviceId);
}
