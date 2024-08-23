package com.themore.moamoatown.clothes.service;

import com.themore.moamoatown.clothes.dto.GetClothesResponseDTO;

import java.util.List;

public interface ClothesService {
    List<GetClothesResponseDTO> getClothesListWithPaging(int page, int size);
}
