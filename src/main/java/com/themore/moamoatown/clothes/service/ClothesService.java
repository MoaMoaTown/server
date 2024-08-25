package com.themore.moamoatown.clothes.service;

import com.themore.moamoatown.clothes.dto.*;

import java.util.List;
/**
 * 옷 관련 비즈니스 로직을 처리하는 서비스 인터페이스.
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
 * 2024.08.25   임재성        위시 상품 조회 추가
 * 2024.08.25   임재성        위시 상품 구매 기능 추가
 * </pre>
 */
public interface ClothesService {
    List<ClothesResponseDTO> getClothesListWithPaging(int page, int size);
    ClothesPurchaseResponseDTO purchaseClothes(ClothesPurchaseRequestDTO requestDTO, Long memberId) throws Exception;

    /**
     * 타운별 위시 아이템 목록을 조회합니다.
     *
     * @param townId 타운 ID.
     * @return 위시 아이템 목록.
     */
    List<WishItemResponseDTO> getWishItemsByTown(Long townId);
    /**
     * 위시 상품을 구매합니다.
     *
     * @param requestDTO 위시 상품 구매 요청 DTO.
     * @return 구매 결과 메시지를 포함한 응답 DTO.
     */
    WishItemPurchaseResponseDTO purchaseWishItem(WishItemPurchaseRequestDTO requestDTO);
}
