package com.themore.moamoatown.quest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 퀘스트 리스트 조회 Response DTO
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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class QuestResponseDTO {
    private Long questId;
    private String title;
    private String description;
    private Long reward;
    private Long capacity;
    private String deadline;
}
