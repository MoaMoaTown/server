package com.themore.moamoatown.quest.controller;

import com.themore.moamoatown.common.annotation.Auth;
import com.themore.moamoatown.common.annotation.MemberId;
import com.themore.moamoatown.common.annotation.TownId;
import com.themore.moamoatown.quest.dto.MemberQuestRequestsResponseDTO;
import com.themore.moamoatown.quest.dto.QuestCreateRequestDTO;
import com.themore.moamoatown.quest.dto.QuestResponseDTO;
import com.themore.moamoatown.quest.dto.QuestStatusListResponseDTO;
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
 * 2024.08.27  임원정        퀘스트 만들기, 퀘스트 현황 조회 추가
 * 2024.08.28  임원정        퀘스트 요청 조회, 퀘스트 요청 수락 추가
 * 2024.08.28  임원정        퀘스트 완료 처리 추가
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
    public ResponseEntity<String> addMemberQuest(@MemberId Long memberId, @PathVariable Long questId) {
        questService.addMemberQuest(memberId, questId);
        return ResponseEntity.ok("퀘스트 수락 요청을 성공했습니다.");
    }

    /**
     * 퀘스트 만들기
     * @param requestDTO
     * @param townId
     * @return
     */
    @Auth(role = Auth.Role.MAYER)
    @PostMapping("/create")
    public ResponseEntity<String> createQuest(@RequestBody QuestCreateRequestDTO requestDTO, @TownId Long townId) {
        questService.createQuest(requestDTO, townId);
        return ResponseEntity.ok("퀘스트 생성에 성공했습니다.");
    }

    /**
     * 퀘스트 현황 리스트 조회
     * @param townId
     * @return
     */
    @Auth(role = Auth.Role.MAYER)
    @GetMapping("/status")
    public ResponseEntity<List<QuestStatusListResponseDTO>> getQuestStatusList(@TownId Long townId) {
        List<QuestStatusListResponseDTO> response = questService.getQuestStatusList(townId);
        return ResponseEntity.ok(response);
    }

    /**
     * 퀘스트 요청 조회
     * @param questId
     * @return
     */
    @Auth(role = Auth.Role.MAYER)
    @GetMapping("/requests/{questId}")
    public ResponseEntity<List<MemberQuestRequestsResponseDTO>> getMemberQuestRequests(@PathVariable Long questId) {
        List<MemberQuestRequestsResponseDTO> response = questService.getMemberQuests(questId);
        return ResponseEntity.ok(response);
    }

    /**
     * 퀘스트 요청 수락
     * @param memberQuestId
     * @return
     */
    @Auth(role = Auth.Role.MAYER)
    @PatchMapping("/select/{memberQuestId}")
    public ResponseEntity<String> selectMemberQuestRequest(@PathVariable Long memberQuestId){
        questService.updateMemberQuestSelected(memberQuestId);
        return ResponseEntity.ok("회원의 퀘스트 요청이 수락되었습니다.");
    }

    /**
     * 퀘스트 완료 처리
     * @param memberQuestId
     * @return
     */
    @Auth(role = Auth.Role.MAYER)
    @PostMapping("/complete/{memberQuestId}")
    public ResponseEntity<String> completeMemberQuest(@PathVariable Long memberQuestId){
        questService.completeMemberQuest(memberQuestId);
        return ResponseEntity.ok("퀘스트가 완료처리 되었습니다.");
    }
}
