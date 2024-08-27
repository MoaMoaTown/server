package com.themore.moamoatown.wish.mapper;


import com.themore.moamoatown.wish.dto.MemberWishRequestsResponseDTO;
import com.themore.moamoatown.wish.dto.WishItemCreateRequestDTO;
import com.themore.moamoatown.wish.dto.WishItemPurchaseInternalRequestDTO;
import com.themore.moamoatown.wish.dto.WishItemResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/**
 * 위시 상품 관련 데이터베이스 작업을 처리하는 매퍼 인터페이스.
 * MyBatis를 사용하여 데이터베이스와 상호작용합니다.
 *
 * @author 임재성
 * @since 2024.08.25
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.25  	임재성        최초 생성
 * 2024.08.25   임재성        위시 상품 조회
 * 2024.08.25   임재성        위시 상품 구매
 * 2024.08.26   임재성        위시 상품 조회 메서드 수정
 * 2024.08.26   임원정        insertWish, deleteWish, deleteMemberWish 메소드 추가
 * 2024.08.26   임재성        위시 상품 구매 메소드 수정
 * 2024.08.26   임원정        updateMemberWishCompleted 메소드 추가
 * 2024.08.27   임원정        selectWishRequestsByTownId 메소드 추가
 * </pre>
 */
@Mapper
public interface WishMapper {
    /**
     * 타운 ID에 해당하는 위시 아이템 목록을 조회합니다.
     *
     * @param townId 타운 ID.
     * @return 위시 아이템 목록.
     */
    List<WishItemResponseDTO> findWishItemsByTownId(Long townId);

    /**
     * 위시 상품 구매 메소드
     * @param internalDTO
     */
    void purchaseWishProcedure(WishItemPurchaseInternalRequestDTO internalDTO);

    
    // 위시 상품 생성
    int insertWish(WishItemCreateRequestDTO createRequestDTO);
    
    /** 위시 삭제 **/
    int deleteMemberWish(Long wishId);  // 멤버 위시 삭제
    int deleteWish(Long wishId);    // 위시 삭제

    // 멤버 위시 상품 완료 처리
    int updateMemberWishCompleted(Long memberWishId);
    // 위시 상품 요청 현황
    List<MemberWishRequestsResponseDTO> selectWishRequestsByTownId(Long townId);
}