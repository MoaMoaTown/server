package com.themore.moamoatown.wish.service;

import com.themore.moamoatown.common.exception.CustomException;
import com.themore.moamoatown.wish.dto.WishItemPurchaseRequestDTO;
import com.themore.moamoatown.wish.dto.WishItemPurchaseResponseDTO;
import com.themore.moamoatown.wish.dto.WishItemResponseDTO;
import com.themore.moamoatown.wish.mapper.WishMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.themore.moamoatown.common.exception.ErrorCode.WISH_INSERT_FAILED;

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
 * </pre>
 */
@Log4j
@Service
@RequiredArgsConstructor
public class WishServiceImpl implements WishService {
    private final WishMapper wishMapper;

    @Transactional(readOnly = true)
    @Override
    public List<WishItemResponseDTO> getWishItemsByTown(Long townId) {
        return wishMapper.findWishItemsByTownId(townId);
    }

    @Transactional
    @Override
    public WishItemPurchaseResponseDTO purchaseWishItem(WishItemPurchaseRequestDTO requestDTO) {
        Long wishId = requestDTO.getWishId();
        Long memberId = requestDTO.getMemberId();

        // MEMBER_WISH 테이블에 구매 내역 추가
        if(1 > wishMapper.insertMemberWish(wishId, memberId)) throw new CustomException(WISH_INSERT_FAILED);



        log.info("멤버 ID: " + memberId + "가 위시 아이템 ID: " + wishId + "를 구매하였습니다.");

        return WishItemPurchaseResponseDTO.builder()
                .message("위시 아이템 " + wishId + "이(가) 구매되었습니다.")
                .build();
    }
}
