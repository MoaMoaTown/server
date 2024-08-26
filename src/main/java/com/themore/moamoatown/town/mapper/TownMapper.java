package com.themore.moamoatown.town.mapper;

import com.themore.moamoatown.town.dto.TownCreateRequestDTO;
import com.themore.moamoatown.town.dto.TownTaxResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 타운 매퍼 인터페이스
 * @author 임원정
 * @since 2024.08.23
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.23  	임원정        최초 생성
 * 2024.08.23  	임원정        타운 만들기 기능 추가
 * 2024.08.26   임원정        타운 세금 현황 조회 추가
 * </pre>
 */

@Mapper
public interface TownMapper {
    /** 타운 만들기 **/
    Long selectIdByTownCode(String townCode);   // 타운 코드로 타운 ID 조회
    int insertTown(TownCreateRequestDTO townCreateRequestDTO);  // 타운 삽입
    int updateMember(@Param("townId") Long townId, @Param("memberId") Long memberId);   // 멤버의 타운아이디 및 역할 업데이트

    /** 타운 세금 현황 조회 **/
    TownTaxResponseDTO selectTotalTaxByTownId(Long townId);


}
