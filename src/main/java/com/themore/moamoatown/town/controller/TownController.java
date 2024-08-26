package com.themore.moamoatown.town.controller;

import com.themore.moamoatown.common.annotation.Auth;
import com.themore.moamoatown.common.annotation.MemberId;
import com.themore.moamoatown.common.annotation.TownId;
import com.themore.moamoatown.town.dto.TownCreateInternalDTO;
import com.themore.moamoatown.town.dto.TownCreateRequestDTO;
import com.themore.moamoatown.town.dto.TownCreateResponseDTO;
import com.themore.moamoatown.town.dto.TownTaxResponseDTO;
import com.themore.moamoatown.town.service.TownService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 타운 컨트롤러
 * @author 임원정
 * @since 2024.08.23
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.23  	임원정        최초 생성
 * 2024.08.23   임원정        타운 만들기 추가
 * 2024.08.24   임원정        타운 만들기 메소드 수정
 * 2024.08.26   임원정        타운 세금 현황 조회 추가
 * </pre>
 */

@Log4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/town",
        produces = "application/json; charset=UTF-8")
public class TownController {
    private final TownService townService;

    /**
     * 타운 만들기
     * @param requestDTO
     * @param memberId
     * @param session
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<TownCreateResponseDTO> createTown(@RequestBody TownCreateRequestDTO requestDTO, @MemberId Long memberId,
                                             HttpSession session) {
        // 타운 생성
        TownCreateInternalDTO internalDTO = townService.createTown(requestDTO, memberId);

        // 세션에 town_id 저장
        session.setAttribute("town_id", internalDTO.getTownId());
        // response 생성
        TownCreateResponseDTO response = TownCreateResponseDTO.builder()
                .townCode(internalDTO.getTownCode())
                .build();

        log.info("타운이 성공적으로 생성되었습니다. \n타운코드: " + internalDTO.getTownCode());
        return ResponseEntity.ok(response);
    }

    /**
     * 타운의 세금현황 조회
     * @param townId
     * @return
     */
    @Auth(role = Auth.Role.MAYER)
    @GetMapping("/tax")
    public ResponseEntity<TownTaxResponseDTO> getTotalTax(@TownId Long townId) {
        TownTaxResponseDTO response = townService.getTotalTax(townId);
        return ResponseEntity.ok(response);
    }
}
