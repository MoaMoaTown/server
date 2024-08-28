package com.themore.moamoatown.quest.service;

import com.themore.moamoatown.common.exception.CustomException;
import com.themore.moamoatown.notification.service.NotificationService;
import com.themore.moamoatown.quest.dto.MemberQuestRequestsResponseDTO;
import com.themore.moamoatown.quest.dto.QuestCreateRequestDTO;
import com.themore.moamoatown.quest.dto.QuestResponseDTO;
import com.themore.moamoatown.quest.dto.QuestStatusListResponseDTO;
import com.themore.moamoatown.quest.mapper.QuestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.themore.moamoatown.common.exception.ErrorCode.*;

/**
 * 퀘스트 서비스 구현체
 * @author 이주현
 * @since 2024.08.26
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.26  이주현        최초 생성
 * 2024.08.26  이주현        퀘스트 수락 요청 기능 추가
 * 2024.08.27  임원정        퀘스트 생성, 퀘스트 현황 리스트 조회 추가
 * 2024.08.28  임원정        퀘스트 요청 조회, 퀘스트 수행인 선정 추가
 * 2024.08.28  임원정        퀘스트 요청 완료 처리 추가
 * 2024.08.28  임원정        알림 전송 로직 추가
 * </pre>
 */

@Service
@RequiredArgsConstructor
public class QuestServiceImpl implements QuestService {
    private final QuestMapper questMapper;
    private final NotificationService notificationService;

    /**
     * 퀘스트 리스트 조회
     * @param townId
     * @return QuestResponseDTO
     */
    @Override
    public List<QuestResponseDTO> getQuests(Long memberId, Long townId) {
        try {
            List<QuestResponseDTO> quests = questMapper.findQuestsByTownId(memberId, townId)
                    .stream()
                    .map(quest -> QuestResponseDTO.builder()
                            .questId(quest.getQuestId())
                            .title(quest.getTitle())
                            .description(quest.getDescription())
                            .reward(quest.getReward())
                            .capacity(quest.getCapacity())
                            .deadline(quest.getDeadline())
                            .status(quest.getStatus())
                            .build())
                    .collect(Collectors.toList());
            return quests;
        } catch (Exception e) {
            throw new CustomException(DATABASE_ERROR);
        }
    }

    /**
     * 퀘스트 수락 요청
     * @param memberId
     * @param questId
     */
    @Override
    @Transactional
    public void addMemberQuest(Long memberId, Long questId) {
        int insertedRows = questMapper.insertMemberQuest(memberId, questId);
        if (insertedRows < 1) {
            throw new CustomException(QUEST_INSERT_FAILED);
        }
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
        if(questMapper.insertQuest(questCreateRequestDTO) != 1) throw new CustomException(QUEST_CREATE_FAILED);
    }

    /**
     * 퀘스트 현황 리스트 조회
     * @param townId
     * @return
     */
    @Override
    public List<QuestStatusListResponseDTO> getQuestStatusList(Long townId) {
        return questMapper.selectQuestStatusListByTownId(townId)
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
     * @return
     */
    @Override
    public List<MemberQuestRequestsResponseDTO> getMemberQuests(Long questId) {
        return questMapper.selectMemberQuestByQuestId(questId)
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
        if(questMapper.updateMemberQuestSelected(memberQuestId) < 1) throw new CustomException(QUEST_SELECTED_FAILED);

        // 퀘스트 요청 수락된 회원 ID 조회
        Long memberId = questMapper.findMemberIdByMemberQuestId(memberQuestId);
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
            questMapper.callCompleteQuestProcedure(memberQuestId);
            // 퀘스트 완료된 회원 ID 조회
            Long memberId = questMapper.findMemberIdByMemberQuestId(memberQuestId);

            // 알림 전송
            String content = "퀘스트가 완료 처리되었습니다. 보상이 지급되었어요!";
            notificationService.notifyMember(memberId, content, "quest");
        } catch (DataAccessException e) {
            throw new CustomException(QUEST_COMPLETE_FAILED);
        }
    }
}
