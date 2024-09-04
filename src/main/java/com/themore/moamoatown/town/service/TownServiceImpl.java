package com.themore.moamoatown.town.service;

import com.themore.moamoatown.common.exception.CustomException;
import com.themore.moamoatown.town.dto.*;
import com.themore.moamoatown.notification.service.NotificationService;
import com.themore.moamoatown.town.mapper.TownMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.themore.moamoatown.common.exception.ErrorCode.*;

/**
 * 타운 서비스 구현체
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
 * 2024.08.26   임원정        getJobRequests, createJob, allowJobRequest 추가
 * 2024.08.26   임원정        위시 상품 생성 메소드 추가
 * 2024.08.26   임원정        멤버 위시 요청 리스트 조회, 멤버 위시 상품 완료 처리 메소드 추가
 * 2024.08.27   임원정        퀘스트 생성, 퀘스트 현황 리스트 조회 추가
 * 2024.08.28   임원정        퀘스트 요청 조회, 퀘스트 수행인 선정, 퀘스트 요청 완료 처리 추가
 * 2024.08.28   임원정        알림 전송 로직 추가
 * 2024.08.30   임원정        급여 지급 프로세스 추가
 * 2024.09.04   임원정        타운 세금 현황 조회 삭제, 페이지네이션 적용
 * </pre>
 */

@Log4j
@Service
@RequiredArgsConstructor
public class TownServiceImpl implements TownService {
    private final TownMapper townMapper;
    private final NotificationService notificationService;

    /**
     * 타운 만들기
     * @param requestDTO
     * @param memberId
     * @return
     */
    @Override
    @Transactional
    public TownCreateInternalDTO createTown(TownCreateRequestDTO requestDTO, Long memberId) {
        // 고유한 타운 코드 생성
        String townCode;
        do {
            townCode = TownCodeGenerator.generateTownCode();
        } while (townMapper.selectIdByTownCode(townCode)!=null);

        TownCreateRequestDTO townCreateRequestDTO = TownCreateRequestDTO.builder()
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .payCycle(requestDTO.getPayCycle())
                .townCode(townCode)
                .build();

        // 생성한 타운 삽입
        if (townMapper.insertTown(townCreateRequestDTO) < 1) throw new CustomException(TOWN_CREATE_FAILED);
        Long townId = townCreateRequestDTO.getTownId();

        // 회원 테이블에 townId 업데이트
        if(townMapper.updateMember(townId, memberId) != 1) throw new CustomException(TOWN_CREATE_FAILED);

        return TownCreateInternalDTO.builder()
                .townId(townId)
                .townCode(townCode)
                .build();
    }

    /**
     * 역할 신청 조회
     * @param townId
     * @param cri
     * @return
     */
    @Override
    @Transactional
    public List<JobRequestsResponseDTO> getJobRequests(Long townId, Criteria cri){
        return townMapper.selectJobRequestByTownId(townId, cri)
                .stream()
                .map(jobRequest -> JobRequestsResponseDTO.builder()
                        .jobRequestId(jobRequest.getJobRequestId())
                        .name(jobRequest.getName())
                        .comments(jobRequest.getComments())
                        .nickName(jobRequest.getNickName())
                        .allowYN(jobRequest.getAllowYN())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 역할 생성
     * @param requestDTO
     * @param townId
     */
    @Override
    @Transactional
    public void createJob (JobCreateRequestDTO requestDTO, Long townId){
        JobCreateRequestDTO jobCreateRequestDTO = JobCreateRequestDTO.builder()
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .pay(requestDTO.getPay())
                .townId(townId)
                .build();
        if(townMapper.insertJob(jobCreateRequestDTO) != 1) throw new CustomException(JOB_CREATE_FAILED);
    }

    /**
     * 역할 선정
     * @param jobRequestId
     */
    @Override
    @Transactional
    public void allowJobRequest(Long jobRequestId) {
        if(townMapper.updateJobRequestAllowed(jobRequestId) != 1) throw new CustomException(JOB_REQUEST_ALLOW_FAILED);
        // 역할이 승인된 회원 정보 조회
        Long memberId = townMapper.findMemberIdByJobRequestId(jobRequestId);

        // 알림 전송
        String content = "축하합니다! 요청하신 역할에 선정되었습니다.";
        notificationService.notifyMember(memberId, content, "job");
    }

    /**
     * 퀘스트 생성
     * @param requestDTO
     * @param townId
     */
    @Override
    @Transactional
    public void createQuest(QuestCreateRequestDTO requestDTO, Long townId) {
        QuestCreateRequestDTO questCreateRequestDTO = QuestCreateRequestDTO.builder()
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .reward(requestDTO.getReward())
                .capacity(requestDTO.getCapacity())
                .deadline(requestDTO.getDeadline())
                .townId(townId)
                .build();
        if(townMapper.insertQuest(questCreateRequestDTO) != 1) throw new CustomException(QUEST_CREATE_FAILED);
    }

    /**
     * 퀘스트 현황 리스트 조회
     * @param townId
     * @param cri
     * @return
     */
    @Override
    public List<QuestStatusListResponseDTO> getQuestStatusList(Long townId, Criteria cri) {
        return townMapper.selectQuestStatusListByTownId(townId, cri)
                .stream()
                .map(questStatus -> QuestStatusListResponseDTO.builder()
                        .questId(questStatus.getQuestId())
                        .title(questStatus.getTitle())
                        .reward(questStatus.getReward())
                        .deadline(questStatus.getDeadline())
                        .requestCnt(questStatus.getRequestCnt())
                        .selectedCnt(questStatus.getSelectedCnt())
                        .capacity(questStatus.getCapacity())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 퀘스트 요청 조회
     * @param questId
     * @param cri
     * @return
     */
    @Override
    public List<MemberQuestRequestsResponseDTO> getMemberQuests(Long questId, Criteria cri) {
        return townMapper.selectMemberQuestByQuestId(questId, cri)
                .stream()
                .map(memberQuest -> MemberQuestRequestsResponseDTO.builder()
                        .memberQuestId(memberQuest.getMemberQuestId())
                        .nickName(memberQuest.getNickName())
                        .status(memberQuest.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 멤버 퀘스트(요청) 승인
     * @param memberQuestId
     */
    @Override
    public void updateMemberQuestSelected(Long memberQuestId) {
        if(townMapper.updateMemberQuestSelected(memberQuestId) < 1) throw new CustomException(QUEST_SELECTED_FAILED);

        // 퀘스트 요청 수락된 회원 ID 조회
        Long memberId = townMapper.findMemberIdByMemberQuestId(memberQuestId);
        // 알림 내용 설정
        String content = "퀘스트 신청이 수락되었습니다. 퀘스트를 수행해주세요";
        // 알림 전송 (eventType은 "quest"로 설정)
        notificationService.notifyMember(memberId, content, "quest");
    }

    /**
     * 멤버 퀘스트 완료 처리
     * @param memberQuestId
     */
    @Override
    @Transactional
    public void completeMemberQuest(Long memberQuestId) {
        try {
            townMapper.callCompleteQuestProcedure(memberQuestId);
            // 퀘스트 완료된 회원 ID 조회
            Long memberId = townMapper.findMemberIdByMemberQuestId(memberQuestId);

            // 알림 전송
            String content = "퀘스트가 완료 처리되었습니다. 보상이 지급되었어요!";
            notificationService.notifyMember(memberId, content, "quest");
        } catch (DataAccessException e) {
            throw new CustomException(QUEST_COMPLETE_FAILED);
        }
    }

    /**
     * 위시 상품 생성
     * @param requestDTO
     * @param townId
     */
    @Override
    @Transactional
    public void createWishItem(WishItemCreateRequestDTO requestDTO, Long townId) {
        WishItemCreateRequestDTO wishItemCreateRequestDTO = WishItemCreateRequestDTO.builder()
                .wishName(requestDTO.getWishName())
                .price(requestDTO.getPrice())
                .townId(townId)
                .build();
        if(townMapper.insertWish(wishItemCreateRequestDTO) != 1) throw new CustomException(WISH_CREATE_FAILED);
    }

    /**
     * 위시 상품 삭제
     * @param wishId
     */
    @Override
    @Transactional
    public void deleteWishItem(Long wishId) {
        // 멤버의 위시 상품 삭제
        townMapper.deleteMemberWish(wishId);
        // 위시 상품 삭제
        if(townMapper.deleteWish(wishId) < 1) throw new CustomException(WISH_DELETE_FAILED);
    }

    /**
     * 멤버 위시 완료 처리
     * @param memberWishId
     */
    @Override
    @Transactional
    public void completeMemberWishItem(Long memberWishId) {
        if(townMapper.updateMemberWishCompleted(memberWishId) != 1) throw new CustomException(WISH_COMPLETE_FAILED);
    }

    /**
     * 위시 상품 요청 리스트 조회
     * @param townId
     * @param cri
     * @return
     */
    @Override
    @Transactional
    public List<MemberWishRequestsResponseDTO> getMemberWishRequests(Long townId, Criteria cri) {
        return townMapper.selectWishRequestsByTownId(townId, cri)
                .stream()
                .map(memberWishRequest -> MemberWishRequestsResponseDTO.builder()
                        .memberWishId(memberWishRequest.getMemberWishId())
                        .wishName(memberWishRequest.getWishName())
                        .nickName(memberWishRequest.getNickName())
                        .createdAt(memberWishRequest.getCreatedAt())
                        .completeYN(memberWishRequest.getCompleteYN())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 급여 지급 프로세스
     */
    @Override
    @Transactional
    public void processPayroll() {
        try {
            // 급여 지급을 위한 프로시저 호출
            townMapper.callProcessPayrollProcedure();
            log.info("급여 지급이 성공적으로 완료되었습니다.");
        } catch (Exception e) {
            log.error("급여 지급 프로세스 중 오류가 발생했습니다.", e);
        }
    }
}
