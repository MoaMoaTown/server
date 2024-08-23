package com.themore.moamoatown.town.controller;

import com.themore.moamoatown.town.dto.TownCreateRequestDTO;
import com.themore.moamoatown.town.dto.TownCreateResponseDTO;
import com.themore.moamoatown.town.service.TownService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 타운 컨트롤러
 * @author 임원정
 * @since 2024.08.23
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.23  	임원정        최초 생성
 * </pre>
 */

@Log4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/town")
public class TownController {
    private final TownService townService;

    @PostMapping("/create")
    public ResponseEntity<String> createTown(@RequestBody TownCreateRequestDTO requestDTO,
                                                            //@MemberId Long memberId,
                                                            HttpSession session) {
        Long memberId = 5L; //수정 예정
        log.info(requestDTO.toString());

        // 타운 생성
        TownCreateResponseDTO responseDTO = townService.createTown(requestDTO, memberId);
        log.info(responseDTO.getTownId());

        // 세션에 town_id 저장
        session.setAttribute("town_id", responseDTO.getTownId());

        // JSON으로 응답
        return ResponseEntity.ok(responseDTO.getTownCode());
    }
}
