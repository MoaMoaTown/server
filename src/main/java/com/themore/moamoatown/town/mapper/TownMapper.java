package com.themore.moamoatown.town.mapper;

import com.themore.moamoatown.town.dto.TownCreateRequestDTO;
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
 * </pre>
 */

@Mapper
public interface TownMapper {
    // 타운 코드 중복 체크 메서드
    Long selectIdByTownCode(String townCode);
    // 타운 삽입
    int insertTown(TownCreateRequestDTO townCreateRequestDTO);
    // 멤버에 타운아이디 업데이트
    int updateMemberTownId(@Param("townId") Long townId, @Param("memberId") Long memberId);
}
