package com.example.cargo_transportation.service;

import com.example.cargo_transportation.dto.FavorDTO;
import com.example.cargo_transportation.entity.Favor;

import java.util.List;

public interface FavorService {
    List<FavorDTO> getAllFavor(List<Long> ids);

    List<Favor> findFavorsById(List<Long> ids);

    FavorDTO getFavorById(Long favorId);

    Favor findFavorById(Long favorId);

    FavorDTO createFavor(FavorDTO favorDTO);

    FavorDTO updateFavor(FavorDTO favorDTO, Long favorId);

    void deleteFavor(Long favorId);
}
