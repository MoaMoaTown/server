package com.themore.moamoatown.town.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 타운 생성 Request DTO
 * @author 임원정
 * @since 2024.08.23
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.23  	임원정       최초 생성
 * </pre>
 */

@Valid
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TownCreateRequestDTO {
    @Size(min = 1, max = 10, message = "타운의 이름은 1자 이상 10자 이하여야 합니다.")
    private String name;
    @Size(max = 255)
    private String description;
    @NotNull
    private Long payCycle;
    private String townCode;
    private Long townId;
}
