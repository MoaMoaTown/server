package com.themore.moamoatown.clothes.service;

import com.themore.moamoatown.clothes.dto.GetClothesResponseDTO;
import com.themore.moamoatown.clothes.mapper.ClothesMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Log4j
@Service
@RequiredArgsConstructor
public class ClothesServiceImpl implements ClothesService{

    private final ClothesMapper clothesmapper;
    @Override
    public List<GetClothesResponseDTO> getClothesListWithPaging(int page, int size) {
        return clothesmapper.getClothesListWithPaging(page,size);
    }
}
