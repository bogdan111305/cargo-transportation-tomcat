package com.example.cargo_transportation.service;

import com.example.cargo_transportation.dto.PriceDTO;
import com.example.cargo_transportation.entity.Price;

import java.util.List;

public interface PriceService {
    List<PriceDTO> getAllPrice();

    List<PriceDTO> getPricesByIds(List<Integer> ids);

    Price getPriceById(Long priceId);

    PriceDTO createPrice(PriceDTO priceDTO);

    PriceDTO updatePrice(PriceDTO priceDTO);

    void deletePrice(Long priceId);
}
