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
    public List<FavorDTO> getAllFavor() {
        return favorRepository.findAll().stream()
                .map(favor -> modelMapper.map(favor, FavorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FavorDTO> getFavorsByIds(List<Long> ids) {
        return favorRepository.findAllById(ids).stream()
                .map(favor -> modelMapper.map(favor, FavorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Favor getFavorById(Long favorId) {
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
    public FavorDTO updateFavor(FavorDTO favorDTO) {
        Favor favor = getFavorById(favorDTO.getId());

        favor.setName(favorDTO.getName());
        favor.setDescription(favorDTO.getDescription());

        favor = favorRepository.save(favor);
        log.info("The favor: {} is updated" + favor.getName());

        return modelMapper.map(favor, FavorDTO.class);
    }

    @Override
    public void deleteFavor(Long favorId) {
        Favor favor = getFavorById(favorId);

        favorRepository.delete(favor);
        log.info("The favor: {} is saved" + favor.getName());
    }
}
