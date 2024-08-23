package com.themore.moamoatown.clothes.mapper;

import com.themore.moamoatown.clothes.dto.GetClothesResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ClothesMapper {
    List<GetClothesResponseDTO> getClothesListWithPaging(int page, int size);
}
