package com.themore.moamoatown.quest.service;

import com.themore.moamoatown.common.exception.CustomException;
import com.themore.moamoatown.quest.dto.QuestResponseDTO;
import com.themore.moamoatown.quest.mapper.QuestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public List<QuestResponseDTO> getQuests(Long townId) {
        try {
            List<QuestResponseDTO> quests = questMapper.findQuestsByTownId(townId)
                    .stream()
                    .map(quest -> QuestResponseDTO.builder()
                            .questId(quest.getQuestId())
                            .title(quest.getTitle())
                            .description(quest.getDescription())
                            .reward(quest.getReward())
                            .capacity(quest.getCapacity())
                            .deadline(quest.getDeadline())
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

}
