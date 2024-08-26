package com.themore.moamoatown.knowledge.controller;

import com.themore.moamoatown.common.annotation.Auth;
import com.themore.moamoatown.knowledge.dto.KnowledgeListResponseDTO;
import com.themore.moamoatown.knowledge.dto.KnowledgeResponseDTO;
import com.themore.moamoatown.knowledge.service.KnowledgeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 지식 컨트롤러
 * @author 이주현
 * @since 2024.08.26
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.26  이주현        최초 생성
 * 2024.08.26  이주현        지식 리스트 조회 기능 추가
 * </pre>
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/knowledge", produces = "application/json; charset=UTF-8")
@Slf4j
@Auth(role = Auth.Role.CITIZEN)
public class KnowledgeController {
    private final KnowledgeService knowledgeService;

    /**
     * 지식 상세 정보 조회
     * @param knowledgeId 지식 ID
     * @return ResponseEntity
     */
    @GetMapping("/{knowledgeId}")
    public ResponseEntity<KnowledgeResponseDTO> getKnowledgeById(@PathVariable("knowledgeId") Long knowledgeId) {
        KnowledgeResponseDTO response = knowledgeService.getKnowledgeById(knowledgeId);
        return ResponseEntity.ok(response);
    }

    /**
     * 지식 리스트 조회
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<KnowledgeListResponseDTO>> getAllKnowledge(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<KnowledgeListResponseDTO> response = knowledgeService.getAllKnowledge(page, size);
        return ResponseEntity.ok(response);
    }
}
