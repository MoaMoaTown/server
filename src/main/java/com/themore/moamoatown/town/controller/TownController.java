package com.themore.moamoatown.town.controller;

import com.themore.moamoatown.common.annotation.*;
import com.themore.moamoatown.town.dto.MemberQuestRequestsResponseDTO;
import com.themore.moamoatown.town.dto.QuestCreateRequestDTO;
import com.themore.moamoatown.town.dto.QuestStatusListResponseDTO;
import com.themore.moamoatown.town.dto.*;
import com.themore.moamoatown.town.service.TownService;
import com.themore.moamoatown.town.dto.MemberWishRequestsResponseDTO;
import com.themore.moamoatown.town.dto.WishItemCreateRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

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
 * 2024.08.26   임원정        타운 역할 신청 현황, 역할 만들기, 역할 선정 추가
 * 2024.08.26   임원정        위시 상품 생성, 삭제 추가
 * 2024.08.26   임원정        멤버 위시 상품 완료 처리 추가
 * 2024.08.27   임원정        멤버 위시 요청 리스트 조회 추가
 * 2024.08.27   임원정        퀘스트 만들기, 퀘스트 현황 조회 추가
 * 2024.08.28   임원정        퀘스트 요청 조회, 퀘스트 요청 수락, 퀘스트 완료 처리 추가
 * 2024.09.04   임원정        타운 세금 현황 조회 삭제, 페이지네이션 적용
 * </pre>
 */

@Log4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/town",
        produces = "application/json; charset=UTF-8")
@Auth(role = Auth.Role.MAYOR)
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
    public ResponseEntity<TownCreateResponseDTO> createTown(
            @RequestBody TownCreateRequestDTO requestDTO,
            @MemberId Long memberId,
            HttpSession session) {
        // 타운 생성
        TownCreateInternalDTO internalDTO = townService.createTown(requestDTO, memberId);
        // 세션에 town_id 저장
        session.setAttribute("town_id", internalDTO.getTownId());

        TownCreateResponseDTO response = TownCreateResponseDTO.builder()
                .townCode(internalDTO.getTownCode())
                .build();

        log.info("타운이 성공적으로 생성되었습니다. \n타운코드: " + internalDTO.getTownCode());
        return ResponseEntity.ok(response);
    }

    /**
     * 타운 내 역할 신청 현황 조회
     * @param townId
     * @param cri
     * @return
     */
    @GetMapping("/job/requests")
    public ResponseEntity<List<JobRequestsResponseDTO>> getJobRequests(@TownId Long townId, Criteria cri) {
        List<JobRequestsResponseDTO> response = townService.getJobRequests(townId, cri);
        return ResponseEntity.ok(response);
    }

    /**
     * 역할 만들기
     * @param requestDTO
     * @param townId
     * @return
     */
    @PostMapping("/job/create")
    public ResponseEntity<String> createJob(@Valid @RequestBody JobCreateRequestDTO requestDTO, @TownId Long townId){
        townService.createJob(requestDTO, townId);
        return ResponseEntity.ok("역할 생성이 완료 되었습니다.");
    }

    /**
     * 역할 요청 승인(역할 선정)
     * @param jobRequestId
     * @return
     */
    @PatchMapping("/job/allow/{jobRequestId}")
    public ResponseEntity<String> allowJobRequest(@PathVariable Long jobRequestId) {
        townService.allowJobRequest(jobRequestId);
        return ResponseEntity.ok("역할이 선정되었습니다.");
    }

    /**
     * 퀘스트 만들기
     * @param requestDTO
     * @param townId
     * @return
     */
    @PostMapping("/quest/create")
    public ResponseEntity<String> createQuest(@RequestBody QuestCreateRequestDTO requestDTO, @TownId Long townId) {
        townService.createQuest(requestDTO, townId);
        return ResponseEntity.ok("퀘스트 생성에 성공했습니다.");
    }

    /**
     * 퀘스트 현황 리스트 조회
     * @param townId
     * @param cri
     * @return
     */
    @GetMapping("/quest/status")
    public ResponseEntity<List<QuestStatusListResponseDTO>> getQuestStatusList(@TownId Long townId, Criteria cri) {
        List<QuestStatusListResponseDTO> response = townService.getQuestStatusList(townId, cri);
        return ResponseEntity.ok(response);
    }

    /**
     * 퀘스트 요청 조회
     * @param questId
     * @param cri
     * @return
     */
    @GetMapping("/quest/requests/{questId}")
    public ResponseEntity<List<MemberQuestRequestsResponseDTO>> getMemberQuestRequests(@PathVariable Long questId, Criteria cri) {
        List<MemberQuestRequestsResponseDTO> response = townService.getMemberQuests(questId, cri);
        return ResponseEntity.ok(response);
    }

    /**
     * 퀘스트 요청 수락
     * @param memberQuestId
     * @return
     */
    @PatchMapping("/quest/select/{memberQuestId}")
    public ResponseEntity<String> selectMemberQuestRequest(@PathVariable Long memberQuestId){
        townService.updateMemberQuestSelected(memberQuestId);
        return ResponseEntity.ok("회원의 퀘스트 요청이 수락되었습니다.");
    }

    /**
     * 퀘스트 완료 처리
     * @param memberQuestId
     * @return
     */
    @PostMapping("/quest/complete/{memberQuestId}")
    public ResponseEntity<String> completeMemberQuest(@PathVariable Long memberQuestId){
        townService.completeMemberQuest(memberQuestId);
        return ResponseEntity.ok("퀘스트가 완료처리 되었습니다.");
    }

    /**
     * 위시 상품 생성
     * @param requestDTO
     * @param townId
     * @return
     */
    @PostMapping("/wish/create")
    public ResponseEntity<String> createWishItem(@Valid @RequestBody WishItemCreateRequestDTO requestDTO,
                                                 @TownId Long townId) {
        townService.createWishItem(requestDTO, townId);
        return ResponseEntity.ok("위시 상품 생성이 완료 되었습니다.");
    }

    /**
     * 위시 상품 삭제
     * @param wishId
     * @return
     */
    @DeleteMapping("/wish/delete/{wishId}")
    public ResponseEntity<String> deleteWishItem(@PathVariable Long wishId) {
        townService.deleteWishItem(wishId);
        return ResponseEntity.ok("위시 상품 삭제가 완료 되었습니다.");
    }

    /**
     * 멤버 위시 상품 완료
     * @param memberWishId
     * @return
     */
    @PatchMapping("/wish/complete/{memberWishId}")
    public ResponseEntity<String> completeWishItem(@PathVariable Long memberWishId) {
        townService.completeMemberWishItem(memberWishId);
        return ResponseEntity.ok("위시 상품 사용 완료 처리 되었습니다.");
    }

    /**
     * 멤버 위시 요청 리스트 조회
     * @param townId
     * @param cri
     * @return
     */
    @GetMapping("/wish/requests")
    public ResponseEntity<List<MemberWishRequestsResponseDTO>> getMemberWishRequests(@TownId Long townId, Criteria cri) {
        List<MemberWishRequestsResponseDTO> response = townService.getMemberWishRequests(townId, cri);
        return ResponseEntity.ok(response);
    }
}
