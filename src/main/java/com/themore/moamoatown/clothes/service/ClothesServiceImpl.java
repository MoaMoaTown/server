package com.themore.moamoatown.clothes.service;

import com.themore.moamoatown.clothes.dto.GetClothesResponseDTO;
import com.themore.moamoatown.clothes.dto.PostClothesPurchaseRequestDTO;
import com.themore.moamoatown.clothes.dto.PostClothesPurchaseResponseDTO;
import com.themore.moamoatown.clothes.mapper.ClothesMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Log4j
@Service
@RequiredArgsConstructor
public class ClothesServiceImpl implements ClothesService{

    private final ClothesMapper clothesmapper;
    @Override
    public List<GetClothesResponseDTO> getClothesListWithPaging(int page, int size) {
        int offset = page * size;  // offset 계산

        return clothesmapper.getClothesListWithPaging(offset,size);
    }
    @Override
    public PostClothesPurchaseResponseDTO purchaseClothes(PostClothesPurchaseRequestDTO requestDTO, Long memberId) throws Exception {
        // DTO에 memberId 설정
//        requestDTO.setMemberId(memberId);

        // CLOSET 테이블에 데이터 삽입
        clothesmapper.insertIntoCloset(requestDTO.getClothesId(), memberId);

        // 응답 반환
        return new PostClothesPurchaseResponseDTO("옷이 클로젯에 추가되었습니다.");
    }
}
