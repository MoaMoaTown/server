package com.themore.moamoatown.quest.controller;

import com.themore.moamoatown.common.annotation.Auth;
import com.themore.moamoatown.common.annotation.MemberId;
import com.themore.moamoatown.common.annotation.TownId;
import com.themore.moamoatown.quest.dto.QuestResponseDTO;
import com.themore.moamoatown.quest.service.QuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
 * 2024.08.26  이주현        퀘스트 수락 요청 기능 추가
 * </pre>
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/quest", produces = "application/json; charset=UTF-8")
@Slf4j
@Auth(role = Auth.Role.CITIZEN)
public class QuestController {
    private final QuestService questService;

    /**
     * 타운 내 퀘스트 리스트 조회
     * @param townId
     * @return ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<QuestResponseDTO>> getQuests(@MemberId Long memberId, @TownId Long townId) {
        List<QuestResponseDTO> response = questService.getQuests(memberId, townId);
        return ResponseEntity.ok(response);
    }

    /**
     * 퀘스트 수락 요청
     * @param memberId
     * @param questId
     * @return ResponseEntity
     */
    @PostMapping("/{questId}")
    public ResponseEntity<String> addMemberQuest(@MemberId Long memberId, @PathVariable Long questId, @TownId Long townId) {
        questService.addMemberQuest(memberId, questId, townId);
        return ResponseEntity.ok("퀘스트 수락 요청을 성공했습니다.");
    }
}
