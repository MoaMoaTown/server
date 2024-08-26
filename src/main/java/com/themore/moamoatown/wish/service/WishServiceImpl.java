package com.themore.moamoatown.wish.service;

import com.themore.moamoatown.clothes.dto.ClothesPurchaseInternalRequestDTO;
import com.themore.moamoatown.common.exception.CustomException;

import com.themore.moamoatown.wish.dto.WishItemPurchaseInternalRequestDTO;

import com.themore.moamoatown.wish.dto.WishItemCreateRequestDTO;

import com.themore.moamoatown.wish.dto.WishItemPurchaseRequestDTO;
import com.themore.moamoatown.wish.dto.WishItemPurchaseResponseDTO;
import com.themore.moamoatown.wish.dto.WishItemResponseDTO;
import com.themore.moamoatown.wish.mapper.WishMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


import static com.themore.moamoatown.common.exception.ErrorCode.WISH_INSERT_FAILED;

import static com.themore.moamoatown.common.exception.ErrorCode.*;


/**
 * 위시 상품 관련 비즈니스 로직을 처리하는 서비스 구현체.
 * @author 임재성
 * @since 2024.08.24
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.24  	임재성        최초 생성
 * 2024.08.25   임재성        위시 상품 조회 기능 추가
 * 2024.08.25   임재성        위시 상품 구매 기능 추가
 * 2024.08.26   임원정        위시 상품 생성 메소드 추가
 * 2024.08.26   임재성        위시 상품 구매 메소드 수정
 * </pre>
 */
@Log4j
@Service
@RequiredArgsConstructor
public class WishServiceImpl implements WishService {
    private final WishMapper wishMapper;
    /**
     * 타운 ID를 통해 위시 상품 리스트를 조회합니다.
     * @param townId 타운 ID
     * @return 위시 상품 리스트
     */
    @Transactional(readOnly = true)
    @Override
    public List<WishItemResponseDTO> getWishItemsByTown(Long townId) {
        return wishMapper.findWishItemsByTownId(townId);
    }
    /**
     * 주어진 요청 정보를 바탕으로 위시 상품을 구매합니다.
     *
     * @param requestDTO 위시 상품 구매 요청 DTO
     * @return 구매 결과를 나타내는 DTO
     * @throws CustomException 위시 상품 구매 실패 시 예외 발생
     */
    @Transactional
    @Override
    public WishItemPurchaseResponseDTO purchaseWishItem(WishItemPurchaseRequestDTO requestDTO) {
        Long wishId = requestDTO.getWishId();
        Long memberId = requestDTO.getMemberId();

        WishItemPurchaseInternalRequestDTO internalDTO = WishItemPurchaseInternalRequestDTO.builder()
                .memberId(memberId)
                .wishId(wishId)
                .result(BigDecimal.ZERO)
                .build();

        wishMapper.purchaseWishProcedure(internalDTO);

        BigDecimal result = internalDTO.getResult(); // 프로시저 실행 후 결과 가져오기
        log.info("result:" + result);

        // 결과가 1이 아니면 예외 발생
        if (result == null || result.intValue() < 1) {
            throw new CustomException(WISH_INSERT_FAILED);
        }

        // 응답 반환
        return new WishItemPurchaseResponseDTO("위시상품 구매가 완료되었습니다.");
    }





    




    /**
     * 위시 상품 생성
     * @param requestDTO
     * @param townId
     */
    @Override
    @Transactional
    public void createWishItem(WishItemCreateRequestDTO requestDTO, Long townId) {
        WishItemCreateRequestDTO wishItemCreateRequestDTO = WishItemCreateRequestDTO.builder()
                .wishName(requestDTO.getWishName())
                .price(requestDTO.getPrice())
                .townId(townId)
                .build();
        if(wishMapper.insertWish(wishItemCreateRequestDTO) != 1) throw new CustomException(WISH_CREATE_FAILED);
    }
}
