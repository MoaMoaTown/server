package com.themore.moamoatown.job.service;

import com.themore.moamoatown.job.dto.JobResponseDTO;

import java.util.List;

/**
 * JOB 관련 비즈니스 로직을 처리하는 서비스 인터페이스.
 *
 * @author 임재성
 * @since 2024.08.26
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.26  	임재성        최초 생성
 * 2024.08.26   임재성        역할 리스트 조회 기능 추가
 * 2024.08.26   임재성        역할 리스트 조회 메서드 수정
 * </pre>
 */
public interface JobService {
    /**
     * 타운 ID에 따른 JOB 목록 조회
     *
     * @param townId 조회할 타운 ID
     * @return 해당 타운에 속한 JOB 목록
     */
    List<JobResponseDTO> getJobsByTownId(Long townId);
}
