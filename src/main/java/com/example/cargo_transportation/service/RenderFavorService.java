package com.example.cargo_transportation.service;

import com.example.cargo_transportation.dto.RenderFavorDTO;
import com.example.cargo_transportation.entity.RenderFavor;

import java.util.List;

public interface RenderFavorService {
    List<RenderFavorDTO> getAllRenderFavor();

    List<RenderFavorDTO> getRenderFavorsByIds(List<Integer> ids);

    RenderFavor getRenderFavorById(Long renderFavorId);

    RenderFavorDTO createRenderFavor(RenderFavorDTO renderFavorDTO);

    RenderFavorDTO updateRenderFavor(RenderFavorDTO renderFavorDTO);

    void deleteRenderFavor(Long renderFavorId);
}
