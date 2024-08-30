package com.themore.moamoatown.closet.mapper;

import com.themore.moamoatown.closet.dto.GetProfileInternalDTO;
import com.themore.moamoatown.closet.dto.MyClothesResponseDTO;
import com.themore.moamoatown.closet.dto.UpdateProfileInternalDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 옷장 매퍼 인터페이스
 * @author 임원정
 * @since 2024.08.25
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.25  	임원정        최초 생성
 * 2024.08.25  	임원정        selectClothesByMemberId, updateProfile 메소드 추가
 * 2024.08.28   임원정        selectProfileByMemberId 메소드 추가
 * 2024.08.30   임원정        selectClothesByMemberId 메소드 수정
 * </pre>
 */

@Mapper
public interface ClosetMapper {
    // 내 옷 가져오기
    List<MyClothesResponseDTO> selectClothesByMemberId(@Param("memberId") Long memberId, @Param("type") Long type);
    // 프로필 업데이트
    int updateProfile(UpdateProfileInternalDTO internalDTO);
    // 프로필 사진 가져오기
    GetProfileInternalDTO selectProfileByMemberId(Long memberId);
}
