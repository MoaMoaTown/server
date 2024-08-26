package com.themore.moamoatown.quest.controller;

import com.themore.moamoatown.common.annotation.TownId;
import com.themore.moamoatown.quest.dto.QuestResponseDTO;
import com.themore.moamoatown.quest.service.QuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 퀘스트 컨트롤러
 * @author 이주현
 * @since 2024.08.26
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.26  이주현        최초 생성
 * </pre>
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/quest", produces = "application/json; charset=UTF-8")
@Slf4j
public class QuestController {
    private final QuestService questService;

    /**
     * 타운 내 퀘스트 리스트 조회
     * @param townId
     * @return ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<QuestResponseDTO>> getQuests(@TownId Long townId) {
        List<QuestResponseDTO> response = questService.getQuests(townId);
        return ResponseEntity.ok(response);
    }
}
