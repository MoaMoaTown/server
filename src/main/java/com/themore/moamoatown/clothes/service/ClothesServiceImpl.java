package com.themore.moamoatown.clothes.service;

import com.themore.moamoatown.clothes.dto.*;
import com.themore.moamoatown.clothes.mapper.ClothesMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import java.util.List;

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
 * 2024.08.25   임재성        위시 상품 조회 추가
 * 2024.08.25   임재성        위시 상품 구매 기능 추가
 * </pre>
 */
@Log4j
@Service
@RequiredArgsConstructor
public class ClothesServiceImpl implements ClothesService{

    private final ClothesMapper clothesmapper;
    @Override
    public List<ClothesResponseDTO> getClothesListWithPaging(int page, int size) {
        int offset = page * size;  // offset 계산

        return clothesmapper.getClothesListWithPaging(offset,size);
    }
    @Override
    public ClothesPurchaseResponseDTO purchaseClothes(ClothesPurchaseRequestDTO requestDTO, Long memberId) throws Exception {
        // DTO에 memberId 설정
//        requestDTO.setMemberId(memberId);

        // CLOSET 테이블에 데이터 삽입
        clothesmapper.insertIntoCloset(requestDTO.getClothesId(), memberId);

        // 응답 반환
        return new ClothesPurchaseResponseDTO("옷이 클로젯에 추가되었습니다.");
    }

    @Override
    public List<WishItemResponseDTO> getWishItemsByTown(Long townId) {
        return clothesmapper.findWishItemsByTownId(townId);
    }

    @Override
    public WishItemPurchaseResponseDTO purchaseWishItem(WishItemPurchaseRequestDTO requestDTO) {
        Long wishId = requestDTO.getWishId();
        Long memberId = requestDTO.getMemberId();

        // MEMBER_WISH 테이블에 구매 내역 추가
        clothesmapper.insertMemberWish(wishId, memberId);

        log.info("멤버 ID: " + memberId + "가 위시 아이템 ID: " + wishId + "를 구매하였습니다.");

        return WishItemPurchaseResponseDTO.builder()
                .message("위시 아이템 " + wishId + "이(가) 멤버 " + memberId + "에 의해 구매되었습니다.")
                .build();
    }
}
