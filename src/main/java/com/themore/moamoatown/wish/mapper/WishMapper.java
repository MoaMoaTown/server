package com.themore.moamoatown.wish.mapper;

import com.themore.moamoatown.wish.dto.WishItemResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.25  	임재성        최초 생성
 * 2024.08.25   임재성        위시 상품 조회
 * 2024.08.25   임재성        위시 상품 구매
 * 2024.08.26   임재성        위시 상품 조회 메서드 수정
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
     * MEMBER_WISH 테이블에 구매 내역을 추가합니다.
     *
     * @param wishId 구매한 위시 아이템의 ID.
     * @param memberId 구매한 멤버의 ID.
     */
    int insertMemberWish(@Param("wishId") Long wishId, @Param("memberId") Long memberId);
}
