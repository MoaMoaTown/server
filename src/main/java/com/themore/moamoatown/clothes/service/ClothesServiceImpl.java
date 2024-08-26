package com.themore.moamoatown.clothes.service;

import com.themore.moamoatown.clothes.dto.*;
import com.themore.moamoatown.clothes.mapper.ClothesMapper;
import com.themore.moamoatown.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.themore.moamoatown.common.exception.ErrorCode.CLOTH_INSERT_FAILED;

/**
 * 옷 관련 비즈니스 로직을 처리하는 서비스 구현체 클래스.
 * 이 클래스는 {@link ClothesService} 인터페이스를 구현합니다.
 *
 * @author 임재성
 * @since 2024.08.24
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.24  	임재성        최초 생성
 * 2024.08.25  	임재성        옷 조회 기능 추가
 * 2024.08.25  	임재성        옷 구매 기능 추가
 * </pre>
 */
@Log4j
@Service
@RequiredArgsConstructor
public class ClothesServiceImpl implements ClothesService{

    private final ClothesMapper clothesmapper;

    @Transactional(readOnly = true)
    @Override
    public List<ClothesResponseDTO> getClothesListWithPaging(int page, int size) {
        int offset = page * size;  // offset 계산

        return clothesmapper.getClothesListWithPaging(offset,size);
    }

    @Transactional
    @Override
    public ClothesPurchaseResponseDTO purchaseClothes(ClothesPurchaseRequestDTO requestDTO, Long memberId) throws Exception {
        // DTO에 memberId 설정
//        requestDTO.setMemberId(memberId);
        log.info("memberId"+ memberId);

        // CLOSET 테이블에 데이터 삽입
        if(1 > clothesmapper.insertIntoCloset(requestDTO.getClothId(), memberId)) throw new CustomException(CLOTH_INSERT_FAILED);


        // 응답 반환
        return new ClothesPurchaseResponseDTO("옷이 클로젯에 추가되었습니다.");
    }

}
