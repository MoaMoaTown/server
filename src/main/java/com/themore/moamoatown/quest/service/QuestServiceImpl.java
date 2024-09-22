package com.themore.moamoatown.quest.service;

import com.themore.moamoatown.common.exception.CustomException;
import com.themore.moamoatown.member.mapper.MemberMapper;
import com.themore.moamoatown.notification.service.NotificationService;
import com.themore.moamoatown.quest.dto.QuestResponseDTO;
import com.themore.moamoatown.quest.mapper.QuestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.themore.moamoatown.common.exception.ErrorCode.DATABASE_ERROR;
import static com.themore.moamoatown.common.exception.ErrorCode.QUEST_INSERT_FAILED;

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
 * </pre>
 */

@Service
@RequiredArgsConstructor
public class QuestServiceImpl implements QuestService {
    private final QuestMapper questMapper;
    private final MemberMapper memberMapper;
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
    public void addMemberQuest(Long memberId, Long questId, Long townId) {
        int insertedRows = questMapper.insertMemberQuest(memberId, questId);
        if (insertedRows < 1) {
            throw new CustomException(QUEST_INSERT_FAILED);
        }
        // 타운 관리자 조회
        Long townAdminId = memberMapper.findAdminByTownId(townId);

        // 관리자에게 알림 전송
        String content = "퀘스트 수락 요청이 왔어요. 확인해 주세요👀";
        notificationService.notifyMember(townAdminId, content);
    }
}
