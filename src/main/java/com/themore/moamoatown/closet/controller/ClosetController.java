package com.themore.moamoatown.closet.controller;

import com.themore.moamoatown.closet.dto.GetProfileResponseDTO;
import com.themore.moamoatown.closet.dto.MyClothesResponseDTO;
import com.themore.moamoatown.closet.dto.UpdateProfileRequestDTO;
import com.themore.moamoatown.closet.service.ClosetService;
import com.themore.moamoatown.common.annotation.Auth;
import com.themore.moamoatown.common.annotation.MemberId;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 옷장 컨트롤러
 * @author 임원정
 * @since 2024.08.25
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.25  	임원정        최초 생성
 * 2024.08.25   임원정        구매한 옷 조회, 프로필 업데이트 기능 추가
 * 2024.08.28   임원정        프로필 사진 가져오기 추가
 * 2024.08.30   임원정        Auth 추가 및 Rename
 * 2024.08.30   임원정        구매한 옷 타입별로 조회하도록 수정
 * </pre>
 */

@Log4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/closet",
        produces = "application/json; charset=UTF-8")
@Auth(role = Auth.Role.CITIZEN)
public class ClosetController {
    private final ClosetService closetService;

    /**
     * 내가 구매한 옷 조회
     * @param memberId
     * @param type
     * @return
     */
    @GetMapping("/myclothes/{type}")
    public ResponseEntity<List<MyClothesResponseDTO>> getMyClothes(@MemberId Long memberId, @PathVariable Long type) {
        List<MyClothesResponseDTO> response = closetService.getMyClothes(memberId, type);
        return ResponseEntity.ok(response);
    }

    /**
     * 코디한 이미지로 프로필 업데이트
     * @param requestDTO
     * @param memberId
     * @return
     */
    @PostMapping("/profile/update")
    public ResponseEntity<String> updateProfile(@RequestBody UpdateProfileRequestDTO requestDTO, @MemberId Long memberId) {
        closetService.updateProfile(requestDTO, memberId);
        return ResponseEntity.ok("프로필이 성공적으로 변경되었습니다.");
    }

    /**
     * 프로필 사진 가져오기
     * @param memberId
     * @return
     */
    @GetMapping("/profile")
    public ResponseEntity<GetProfileResponseDTO> getProfile(@MemberId Long memberId) {
        GetProfileResponseDTO response = closetService.getProfile(memberId);
        return ResponseEntity.ok(response);
    }
}
