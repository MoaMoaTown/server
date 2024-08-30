package com.themore.moamoatown.coordi.service;

import com.themore.moamoatown.common.exception.CustomException;
import com.themore.moamoatown.coordi.dto.*;
import com.themore.moamoatown.coordi.mapper.CoordiMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static com.themore.moamoatown.common.exception.ErrorCode.UPDATE_PROFILE_FAILED;

/**
 * 코디 서비스 구현체
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

@Log4j
@Service
@RequiredArgsConstructor
public class CoordiServiceImpl implements CoordiService{
    private final CoordiMapper coordiMapper;

    /**
     * 내가 구매한 옷 가져오기
     * @param memberId
     * @return
     */
    @Override
    @Transactional
    public List<MyClothesResponseDTO> getMyClothes(Long memberId) {
        return coordiMapper.selectClothesByMemberId(memberId)
                .stream()
                .map(clothes -> MyClothesResponseDTO.builder()
                        .clothId(clothes.getClothId())
                        .brand(clothes.getBrand())
                        .name(clothes.getName())
                        .type(clothes.getType())
                        .imgUrl(clothes.getImgUrl())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 프로필 업데이트
     * @param requestDTO
     * @param memberId
     */
    @Override
    @Transactional
    public void updateProfile(UpdateProfileRequestDTO requestDTO, Long memberId) {
        // byte로 변환
        byte[] decodedImage = requestDTO.getProfile().getBytes();
        UpdateProfileInternalDTO internalDTO = UpdateProfileInternalDTO.builder()
                .memberId(memberId)
                .profileImage(decodedImage)
                .build();

        if(coordiMapper.updateProfile(internalDTO)!=1) throw new CustomException(UPDATE_PROFILE_FAILED);
    }

    /**
     * 프로필 사진 가져오기
     * @param memberId
     * @return
     */
    @Override
    public GetProfileResponseDTO getProfile(Long memberId) {
        GetProfileInternalDTO getProfileInternalDTO = coordiMapper.selectProfileByMemberId(memberId);
        // BLOB 데이터를 Base64로 인코딩
        String base64Image = Base64.getEncoder().encodeToString(getProfileInternalDTO.getProfile());
        return GetProfileResponseDTO.builder()
                .encodedProfileImage(base64Image)
                .build();
    }
}
