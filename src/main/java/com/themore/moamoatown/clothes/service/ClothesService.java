package com.themore.moamoatown.clothes.service;

import com.themore.moamoatown.clothes.dto.GetClothesResponseDTO;
import com.themore.moamoatown.clothes.dto.PostClothesPurchaseRequestDTO;
import com.themore.moamoatown.clothes.dto.PostClothesPurchaseResponseDTO;

import java.util.List;

public interface ClothesService {
    List<GetClothesResponseDTO> getClothesListWithPaging(int page, int size);
    PostClothesPurchaseResponseDTO purchaseClothes(PostClothesPurchaseRequestDTO requestDTO, Long memberId) throws Exception;

}
