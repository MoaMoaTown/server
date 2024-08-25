package com.themore.moamoatown.clothes.mapper;

import com.themore.moamoatown.clothes.dto.GetClothesResponseDTO;
import com.themore.moamoatown.clothes.dto.PostClothesPurchaseRequestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ClothesMapper {
    List<GetClothesResponseDTO> getClothesListWithPaging(@Param("offset") int offset, @Param("size") int size);
    void insertIntoCloset(@Param("clothesId") Long clothesId, @Param("memberId") Long memberId);

}
