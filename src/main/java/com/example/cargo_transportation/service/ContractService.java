package com.example.cargo_transportation.service;

import com.example.cargo_transportation.modal.dto.ContractDTO;
import com.example.cargo_transportation.modal.dto.PriceDTO;
import com.example.cargo_transportation.entity.Contract;

import java.util.List;

public interface ContractService {
    ContractDTO getContractById(Long contractId);

    List<ContractDTO> getAllContract();

    List<PriceDTO> getPricesFromContract(Long contractId) ;

    ContractDTO createContract(ContractDTO contractDTO);

    ContractDTO updateContract(ContractDTO contractDTO, Long contractId);

    void deleteContract(Long contractId);

    List<PriceDTO> addPricesFromContract(Long contractId, List<PriceDTO> services);

    void addPriceFromContract(Long contractId, Long serviceId, Integer count);

    void removePriceFromContract(Long contractId, Long serviceId);

    Contract findContractById(Long contractId);
}
