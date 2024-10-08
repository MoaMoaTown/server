package com.themore.moamoatown.quest.service;

import com.themore.moamoatown.quest.dto.QuestResponseDTO;

import java.util.List;

/**
 * 퀘스트 서비스 인터페이스
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

public interface QuestService {
    // 퀘스트 리스트 조회
    List<QuestResponseDTO> getQuests(Long memberId, Long townId);

    // 퀘스트 수락 요청
    void addMemberQuest(Long memberId, Long questId, Long townId);
}
