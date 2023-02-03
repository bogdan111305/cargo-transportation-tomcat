package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.dto.ClientDTO;
import com.example.cargo_transportation.dto.FavorDTO;
import com.example.cargo_transportation.entity.Client;
import com.example.cargo_transportation.entity.Favor;
import com.example.cargo_transportation.exception.EntityNotFoundException;
import com.example.cargo_transportation.repo.FavorRepository;
import com.example.cargo_transportation.service.FavorService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class FavorServiceImpl implements FavorService {

    private final FavorRepository favorRepository;
    private final ModelMapper modelMapper;

    public FavorServiceImpl(FavorRepository favorRepository, ModelMapper modelMapper) {
        this.favorRepository = favorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<FavorDTO> getAllFavor(List<Long> ids) {
        List<Favor> favors = null;
        if (ids != null && !ids.isEmpty())
            favors = findFavorsById(ids);
        else
            favors = favorRepository.findAll();
        return favors.stream()
                .map(favor -> modelMapper.map(favor, FavorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Favor> findFavorsById(List<Long> ids) {
        return favorRepository.findAllById(ids);
    }

    @Override
    public FavorDTO getFavorById(Long favorId) {
        return modelMapper.map(findFavorById(favorId), FavorDTO.class);
    }

    @Override
    public Favor findFavorById(Long favorId) {
        return favorRepository.findById(favorId)
                .orElseThrow(() -> new EntityNotFoundException("Favor not found with id: " + favorId));
    }

    @Override
    public FavorDTO createFavor(FavorDTO favorDTO) {
        Favor favor = modelMapper.map(favorDTO, Favor.class);

        favor = favorRepository.save(favor);
        log.info("The favor: {} is saved" + favor.getName());

        return modelMapper.map(favor, FavorDTO.class);
    }

    @Override
    public FavorDTO updateFavor(FavorDTO favorDTO, Long favorId) {
        Favor favor = findFavorById(favorId);

        favor.setName(favorDTO.getName());
        favor.setDescription(favorDTO.getDescription());

        favor = favorRepository.save(favor);
        log.info("The favor: {} is updated" + favor.getName());

        return modelMapper.map(favor, FavorDTO.class);
    }

    @Override
    public void deleteFavor(Long favorId) {
        Favor favor = findFavorById(favorId);

        favorRepository.delete(favor);
        log.info("The favor: {} is saved" + favor.getName());
    }
}
