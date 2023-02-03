package com.example.cargo_transportation.controller;

import com.example.cargo_transportation.dto.FavorDTO;
import com.example.cargo_transportation.service.FavorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favor")
public class FavorRestController {

    private final FavorService favorService;

    @Autowired
    public FavorRestController(FavorService favorService) {
        this.favorService = favorService;
    }

    @GetMapping("/favors")
    public List<FavorDTO> getAllFavor(@RequestBody(required = false) List<Long> ids) {
        return favorService.getAllFavor(ids);
    }

    @GetMapping("/{favorId}")
    public FavorDTO getFavorById(@PathVariable Long favorId) {
        return favorService.getFavorById(favorId);
    }

    @PostMapping()
    public FavorDTO createFavor(@Valid @RequestBody FavorDTO favorDTO) {
        return favorService.createFavor(favorDTO);
    }

    @PutMapping("/{favorId}")
    public FavorDTO updateFavor(@Valid @RequestBody FavorDTO favorDTO, @PathVariable Long favorId) {
        return favorService.updateFavor(favorDTO, favorId);
    }

    @DeleteMapping("/{favorId}")
    public void deleteFavor(@PathVariable Long favorId) {
        favorService.deleteFavor(favorId);
    }
}
