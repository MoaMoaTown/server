package com.themore.moamoatown.closet.service;

import com.themore.moamoatown.closet.dto.GetProfileResponseDTO;
import com.themore.moamoatown.closet.dto.MyClothesResponseDTO;
import com.themore.moamoatown.closet.dto.UpdateProfileRequestDTO;

import java.util.List;

/**
 * 코디 서비스 인터페이스
 * @author 임원정
 * @since 2024.08.25
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.25  	임원정        최초 생성
 * 2024.08.25  	임원정        구매한 옷 가져오기, 프로필 업데이트 메소드 추가
 * 2024.08.28   임원정        프로필 사진 가져오기
 * </pre>
 */

public interface ClosetService {
    // 내가 구매한 옷 가져오기
    List<MyClothesResponseDTO> getMyClothes(Long memberId);
    // 프로필 업데이트
    void updateProfile(UpdateProfileRequestDTO requestDTO, Long memberId);
    // 프로필 사진 가져오기
    GetProfileResponseDTO getProfile(Long memberId);
}
