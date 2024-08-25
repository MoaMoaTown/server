package com.themore.moamoatown.job.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * JOB 목록 조회 응답 DTO 클래스.
 *
 * JOB에 대한 정보를 담고 있습니다.
 *
 * @author 임재성
 * @since 2024.08.26
 * @version 1.0
 */
@Builder
@Getter
public class JobResponseDTO {
    private Long jobId;
    private Long townId;
    private String name;
    private String description;
    private Long pay;
}
