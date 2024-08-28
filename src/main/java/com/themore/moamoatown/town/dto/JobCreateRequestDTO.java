package com.themore.moamoatown.town.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 역할 생성 Request DTO
 * @author 임원정
 * @since 2024.08.26
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.26  	임원정       최초 생성
 * </pre>
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobCreateRequestDTO {
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Long pay;
    private Long townId;
}
