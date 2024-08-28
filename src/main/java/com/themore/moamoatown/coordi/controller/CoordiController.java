package com.themore.moamoatown.coordi.controller;

import com.themore.moamoatown.common.annotation.MemberId;
import com.themore.moamoatown.coordi.dto.GetProfileResponseDTO;
import com.themore.moamoatown.coordi.dto.MyClothesResponseDTO;
import com.themore.moamoatown.coordi.dto.UpdateProfileRequestDTO;
import com.themore.moamoatown.coordi.service.CoordiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 코디 컨트롤러
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
 * </pre>
 */

@Log4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/coordi",
        produces = "application/json; charset=UTF-8")
public class CoordiController {
    private final CoordiService coordiService;

    /**
     * 내가 구매한 옷 조회
     * @param memberId
     * @return
     */
    @GetMapping("/myclothes")
    public ResponseEntity<List<MyClothesResponseDTO>> getMyClothes(@MemberId Long memberId) {
        List<MyClothesResponseDTO> response = coordiService.getMyClothes(memberId);
        return ResponseEntity.ok(response);
    }

    /**
     * 코디한 이미지로 프로필 업데이트
     * @param requestDTO
     * @param memberId
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity<String> updateProfile(@RequestBody UpdateProfileRequestDTO requestDTO, @MemberId Long memberId) {
        coordiService.updateProfile(requestDTO, memberId);
        return ResponseEntity.ok("프로필이 성공적으로 변경되었습니다.");
    }

    /**
     * 프로필 사진 가져오기
     * @param memberId
     * @return
     */
    @GetMapping("/profile")
    public ResponseEntity<GetProfileResponseDTO> getProfile(@MemberId Long memberId) {
        GetProfileResponseDTO response = coordiService.getProfile(memberId);
        return ResponseEntity.ok(response);
    }
}
