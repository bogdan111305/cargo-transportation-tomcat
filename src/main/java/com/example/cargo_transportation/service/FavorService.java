package com.example.cargo_transportation.service;

import com.example.cargo_transportation.dto.FavorDTO;
import com.example.cargo_transportation.entity.Favor;

import java.util.List;

public interface FavorService {
    List<FavorDTO> getAllFavor();

    List<FavorDTO> getFavorsByIds(List<Integer> ids);

    Favor getFavorById(Long favorId);

    FavorDTO createFavor(FavorDTO favorDTO);

    FavorDTO updateFavor(FavorDTO favorDTO);

    void deleteFavor(Long favorId);
}
