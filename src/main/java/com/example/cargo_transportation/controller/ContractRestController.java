package com.example.cargo_transportation.controller;

import com.example.cargo_transportation.dto.ContractDTO;
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
    public List<ContractDTO> getAllContract(@RequestBody List<Long> ids) {
        return contractService.getAllContract(ids);
    }

    @GetMapping("/{contractId}")
    public ContractDTO getContractById(@PathVariable Long contractId) {
        return contractService.getContractById(contractId);
    }

    @PostMapping()
    public ContractDTO createContract(@Valid @RequestBody ContractDTO contractDTO) {
        return contractService.createContract(contractDTO);
    }

    @PutMapping("/{contractId}")
    public ContractDTO updateContract(@Valid @RequestBody ContractDTO contractDTO, @PathVariable Long contractId) {
        return contractService.updateContract(contractDTO, contractId);
    }

    @DeleteMapping("/{contractId}")
    public void deleteContract(@PathVariable Long contractId) {
        contractService.deleteContract(contractId);
    }
}
