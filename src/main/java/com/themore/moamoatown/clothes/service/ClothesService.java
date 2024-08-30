package com.themore.moamoatown.clothes.service;

import com.themore.moamoatown.clothes.dto.*;
import com.themore.moamoatown.wish.dto.WishItemPurchaseRequestDTO;
import com.themore.moamoatown.wish.dto.WishItemPurchaseResponseDTO;
import com.themore.moamoatown.wish.dto.WishItemResponseDTO;

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
 * </pre>
 */
public interface ClothesService {
    List<ClothesResponseDTO> getClothesListWithPaging();
//    ClothesPurchaseResponseDTO purchaseClothes(ClothesPurchaseRequestDTO requestDTO, Long memberId) throws Exception;
    ClothesPurchaseResponseDTO purchaseClothes(Long ClothId, Long memberId) throws Exception;


}
