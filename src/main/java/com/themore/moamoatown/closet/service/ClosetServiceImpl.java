package com.themore.moamoatown.closet.service;

import com.themore.moamoatown.common.exception.CustomException;
import com.themore.moamoatown.closet.dto.*;
import com.themore.moamoatown.closet.mapper.ClosetMapper;
import com.themore.moamoatown.common.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static com.themore.moamoatown.common.exception.ErrorCode.UPDATE_PROFILE_FAILED;

/**
 * 옷장 서비스 구현체
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
 * 2024.08.30   임원정        getMyClothes 메소드 수정
 * 2024.09.01   임원정        getMyClothes에서 imageUrl을 base64인코딩 하도록 수정
 * </pre>
 */

@Log4j
@Service
@RequiredArgsConstructor
public class ClosetServiceImpl implements ClosetService {
    private final ClosetMapper closetMapper;

    /**
     * 내가 구매한 옷 가져오기
     * @param memberId
     * @param type
     * @return
     */
    @Override
    @Transactional
    public List<MyClothesResponseDTO> getMyClothes(Long memberId, Long type) {
        return closetMapper.selectClothesByMemberId(memberId, type)
                .stream()
                .map(clothes -> {
                    String base64Image = "";
                    try {
                        base64Image = ImageUtils.encodeImageToBase64(clothes.getImage()); // URL을 Base64로 인코딩
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    base64Image = "data:image/png;base64," + base64Image;
                    return MyClothesResponseDTO.builder()
                            .clothId(clothes.getClothId())
                            .brand(clothes.getBrand())
                            .name(clothes.getName())
                            .type(clothes.getType())
                            .image(base64Image)
                            .build();
                })
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

        if(closetMapper.updateProfile(internalDTO)!=1) throw new CustomException(UPDATE_PROFILE_FAILED);
    }

    /**
     * 프로필 사진 가져오기
     * @param memberId
     * @return
     */
    @Override
    public GetProfileResponseDTO getProfile(Long memberId) {
        GetProfileInternalDTO getProfileInternalDTO = closetMapper.selectProfileByMemberId(memberId);
        return GetProfileResponseDTO.builder()
                .encodedProfileImage(new String(getProfileInternalDTO.getProfile()))
                .build();
    }
}
