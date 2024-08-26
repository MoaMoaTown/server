package com.themore.moamoatown.job.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 역할 요청을 위한 DTO 클래스.
 *
 * @author 임재성
 * @since 2024.08.26
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.26  	임재성        최초 생성
 * 2024.08.26   임재성        역할 리스트 조회 요청 기능 추가
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobRequestDTO {
    private Long jobId;
    private Long memberId; // 실제로는 세션에서 가져올 예정
    private String comments;
}
