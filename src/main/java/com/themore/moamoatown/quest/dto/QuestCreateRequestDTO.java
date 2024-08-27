package com.themore.moamoatown.quest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 퀘스트 생성 Request DTO
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
public class QuestCreateRequestDTO {
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private Long reward;
    @NotNull
    private Long capacity;
    @NotNull
    private String deadline;
    private Long townId;
}