package com.themore.moamoatown.job.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 역할 요청 리스트 조회 Response DTO
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
public class JobRequestsResponseDTO {
    private Long jobRequestId;
    private String name;
    private String comments;
    private String nickName;
    private Character allowYN;
}
