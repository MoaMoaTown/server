package com.themore.moamoatown.quest.mapper;

import com.themore.moamoatown.quest.dto.MemberQuestRequestsResponseDTO;
import com.themore.moamoatown.quest.dto.QuestCreateRequestDTO;
import com.themore.moamoatown.quest.dto.QuestResponseDTO;
import com.themore.moamoatown.quest.dto.QuestStatusListResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 퀘스트 매퍼 인터페이스
 * @author 이주현
 * @since 2024.08.26
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.26  이주현        최초 생성
 * 2024.08.26  이주현        퀘스트 수락 요청 기능 추가
 * 2024.08.27  임원정        insertQuest, selectQuestStatusListByTownId 메소드 추가
 * 2024.08.28  임원정        selectMemberQuestByQuestId, updateMemberQuestSelected 메소드 추가
 * 2024.08.28  임원정        callCompleteQuestProcedure 메소드 추가
 * </pre>
 */

@Mapper
public interface QuestMapper {
    // 퀘스트 리스트 조회
    List<QuestResponseDTO> findQuestsByTownId(@Param("memberId") Long memberId, @Param("townId") Long townId);

    // 퀘스트 수락 요청
    int insertMemberQuest(@Param("memberId") Long memberId, @Param("questId") Long questId);
    // 퀘스트 생성
    int insertQuest(QuestCreateRequestDTO questCreateRequestDTO);
    // 퀘스트 현황 리스트 조회
    List<QuestStatusListResponseDTO> selectQuestStatusListByTownId(Long townId);
    // 퀘스트 신청 조회
    List<MemberQuestRequestsResponseDTO> selectMemberQuestByQuestId(Long questId);
    // 퀘스트 수행인 선정
    int updateMemberQuestSelected(Long memberQuestId);
    // 퀘스트 완료 처리
    void callCompleteQuestProcedure(Long memberQuestId);
}