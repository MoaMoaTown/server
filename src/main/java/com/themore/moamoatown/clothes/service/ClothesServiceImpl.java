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

    @Transactional(readOnly = true)
    @Override
    public List<ClothesResponseDTO> getClothesListWithPaging(int page, int size) {
        int offset = page * size;  // offset 계산

        return clothesmapper.getClothesListWithPaging(offset,size);
    }


//@Transactional
//@Override
//public ClothesPurchaseResponseDTO purchaseClothes(ClothesPurchaseRequestDTO requestDTO, Long memberId) throws Exception {
//    log.info("memberId: " + memberId);
//
//    // 프로시저 호출 및 결과 확인
//    Map<String, Object> params = new HashMap<>();
//    params.put("memberId", memberId);
//    params.put("clothId", requestDTO.getClothId());
//    params.put("result", null);   // 미리 공간을 할당해줘야 나중에 -1또는1이 담겨서 return 될 수 있음.
//
//    clothesmapper.purchaseClothesProcedure(params);
//
//    BigDecimal result = (BigDecimal) params.get("result");
//    log.info("result:" + result);
//
//    // 결과가 1이 아니면 예외 발생
//    if (result.intValue() < 1) {
//        throw new CustomException(CLOTH_INSERT_FAILED);
//    }
//
//    // 응답 반환
//    return new ClothesPurchaseResponseDTO("옷이 클로젯에 추가되었습니다.");
//}
//@Transactional
//@Override
//public ClothesPurchaseResponseDTO purchaseClothes(ClothesPurchaseRequestDTO requestDTO, Long memberId) throws Exception {
//    log.info("memberId: " + memberId);
//
//    BigDecimal result = null;
//
//    // 프로시저 호출 및 결과 확인
//    clothesmapper.purchaseClothesProcedure(memberId, requestDTO.getClothId(), result);
//
//    log.info("result:" + result);
//
//    // 결과가 1이 아니면 예외 발생
//    if (result == null || result.intValue() < 1) {
//        throw new CustomException(CLOTH_INSERT_FAILED);
//    }
//
//    // 응답 반환
//    return new ClothesPurchaseResponseDTO("옷이 클로젯에 추가되었습니다.");
//}
@Transactional
@Override
public ClothesPurchaseResponseDTO purchaseClothes(ClothesPurchaseRequestDTO requestDTO, Long memberId) throws Exception {
    log.info("memberId: " + memberId);

    // 내부 로직용 DTO 생성
    ClothesPurchaseInternalRequestDTO internalDTO = ClothesPurchaseInternalRequestDTO.builder()
            .memberId(memberId)
            .clothId(requestDTO.getClothId())
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
