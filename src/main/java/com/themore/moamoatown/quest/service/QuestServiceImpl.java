package com.themore.moamoatown.quest.service;

import com.themore.moamoatown.common.exception.CustomException;
import com.themore.moamoatown.quest.dto.QuestCreateRequestDTO;
import com.themore.moamoatown.quest.dto.QuestResponseDTO;
import com.themore.moamoatown.quest.dto.QuestStatusListResponseDTO;
import com.themore.moamoatown.quest.mapper.QuestMapper;
import lombok.RequiredArgsConstructor;
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
 * </pre>
 */

@Service
@RequiredArgsConstructor
public class QuestServiceImpl implements QuestService {
    private final QuestMapper questMapper;

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

            if (quests.isEmpty()) {
                throw new CustomException(QUESTS_NOT_FOUND);
            }

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
}
