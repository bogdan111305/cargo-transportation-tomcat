package com.example.cargo_transportation.controller;

import com.example.cargo_transportation.modal.dto.ContractRequest;
import com.example.cargo_transportation.modal.dto.ContractResponse;
import com.example.cargo_transportation.modal.dto.PriceDTO;
import com.example.cargo_transportation.service.ContractService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contract")
public class ContractRestController {
    private final ContractService contractService;

    @Autowired
    public ContractRestController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping("/contracts")
    public List<ContractResponse> getAllContract() {
        return contractService.getAllContract();
    }

    @GetMapping("/{contractId}")
    public ContractResponse getContractById(@PathVariable Long contractId) {
        return contractService.getContractById(contractId);
    }

    @PostMapping()
    public ContractResponse createContract(@Valid @RequestBody ContractRequest contract) {
        return contractService.createContract(contract);
    }

    @PutMapping("/{contractId}")
    public ContractResponse updateContract(@Valid @RequestBody ContractRequest contract,
                                          @PathVariable Long contractId) {
        return contractService.updateContract(contract, contractId);
    }

    @DeleteMapping("/{contractId}")
    public void deleteContract(@PathVariable Long contractId) {
        contractService.deleteContract(contractId);
    }

    @GetMapping("/{contractId}/prices")
    public List<PriceDTO> getPricesFromContract(@PathVariable Long contractId) {
        return contractService.getPricesFromContract(contractId);
    }

    @PostMapping("/{contractId}/prices")
    public List<PriceDTO> addPricesFromContract(@PathVariable Long contractId,
                                                @Valid @RequestBody List<PriceDTO> services) {
        return contractService.addPricesFromContract(contractId, services);
    }

    @PostMapping("/{contractId}/price/{serviceId}")
    public void addPriceFromContract(@PathVariable("contractId") Long contractId,
                                     @PathVariable("serviceId") Long serviceId,
                                     Integer count) {
        contractService.addPriceFromContract(contractId, serviceId, count);
    }

    @DeleteMapping("/{contractId}/price/{serviceId}")
    public void removePriceFromContract(@PathVariable("contractId") Long contractId,
                                        @PathVariable("serviceId") Long serviceId) {
        contractService.removePriceFromContract(contractId, serviceId);
    }
}
