package com.themore.moamoatown.clothes.service;

import com.themore.moamoatown.clothes.dto.*;
import com.themore.moamoatown.clothes.mapper.ClothesMapper;
import com.themore.moamoatown.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.themore.moamoatown.common.exception.ErrorCode.CLOTH_ALREADY_PURCHASED;
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
 * 2024.08.26   임재성        옷 구매 메서드 수정
 * </pre>
 */
@Log4j
@Service
@RequiredArgsConstructor
public class ClothesServiceImpl implements ClothesService{

    private final ClothesMapper clothesmapper;
    /**
     * 페이지와 사이즈에 따른 옷 목록을 페이징 처리하여 가져옵니다.
     * @return 옷 목록의 리스트
     */
//    @Transactional(readOnly = true)
//    @Override
//    public List<ClothesResponseDTO> getClothesListWithPaging() {
//
//        return clothesmapper.getClothesListWithPaging();
//    }
    @Transactional(readOnly = true)
    @Override
    public List<ClothesResponseDTO> getClothesListWithPaging(int page, int size) {
        int offset = page * size;
        return clothesmapper.getClothesListWithPaging(offset, size);
    }



    /**
     * 주어진 요청 정보를 바탕으로 옷을 구매합니다.
     * 프로시저 호출을 통해 구매를 처리하고, 결과에 따라 성공 여부를 확인합니다.
     *
     * @param memberId 회원 ID
     * @return 구매 결과를 나타내는 DTO
     * @throws Exception 옷 구매 실패 시 예외 발생
     */
    @Transactional
    @Override
    public ClothesPurchaseResponseDTO purchaseClothes(Long ClothId, Long memberId) throws Exception {
        log.info("memberId: " + memberId);

        // 중복 구매 확인
        boolean isAlreadyPurchased = clothesmapper.isClothAlreadyInCloset(memberId, ClothId);
        if (isAlreadyPurchased) {
            throw new CustomException(CLOTH_ALREADY_PURCHASED); // 예외 메시지는 적절히 정의하세요
        }

        // 내부 로직용 DTO 생성
        ClothesPurchaseInternalRequestDTO internalDTO = ClothesPurchaseInternalRequestDTO.builder()
                .memberId(memberId)
                .clothId(ClothId)
                .result(BigDecimal.ZERO) // 초기값 설정
                .build();

        // 프로시저 호출 및 결과 확인
        clothesmapper.purchaseClothesProcedure(internalDTO);

        BigDecimal result = internalDTO.getResult(); // 프로시저 실행 후 결과 가져오기
        log.info("result:" + result);

        // 결과가 1이 아니면 예외 발생
        if (result == null || result.intValue() < 1) {
            throw new CustomException(CLOTH_INSERT_FAILED);
        }

        // 응답 반환
        return new ClothesPurchaseResponseDTO("옷이 클로젯에 추가되었습니다.");
    }







}
