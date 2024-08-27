package com.themore.moamoatown.quest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 퀘스트 현황 조회 Response DTO
 * @author 임원정
 * @since 2024.08.27
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.27  	임원정        최초 생성
 * </pre>
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestStatusListResponseDTO {
    private Long questId;
    private String title;
    private Long reward;
    private String deadline;
    private Long requestCnt;
    private Long selectedCnt;
    private Long capacity;
}
