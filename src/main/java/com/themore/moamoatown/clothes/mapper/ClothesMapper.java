package com.themore.moamoatown.clothes.mapper;

import com.themore.moamoatown.clothes.dto.ClothesResponseDTO;
import com.themore.moamoatown.clothes.dto.WishItemResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 옷 관련 데이터베이스 작업을 처리하는 매퍼 인터페이스.
 * MyBatis를 사용하여 데이터베이스와 상호작용합니다.
 *
 * @author 임재성
 * @since 2024.08.26
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.26  	임재성        최초 생성
 * 2024.08.25  	임재성        옷 목록 조회 기능 추가
 * 2024.08.25  	임재성        클로젯에 옷 추가 기능 추가
 * 2024.08.25   임재성        위시 상품 조회

 * </pre>
 */
@Mapper
public interface ClothesMapper {
    List<ClothesResponseDTO> getClothesListWithPaging(@Param("offset") int offset, @Param("size") int size);
    void insertIntoCloset(@Param("clothesId") Long clothesId, @Param("memberId") Long memberId);

    /**
     * 타운 ID에 해당하는 위시 아이템 목록을 조회합니다.
     *
     * @param townId 타운 ID.
     * @return 위시 아이템 목록.
     */
    List<WishItemResponseDTO> findWishItemsByTownId(@Param("townId") Long townId);
    /**
     * MEMBER_WISH 테이블에 구매 내역을 추가합니다.
     *
     * @param wishId 구매한 위시 아이템의 ID.
     * @param memberId 구매한 멤버의 ID.
     */
    void insertMemberWish(@Param("wishId") Long wishId, @Param("memberId") Long memberId);
}
