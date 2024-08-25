package com.themore.moamoatown.job.service;

import com.themore.moamoatown.job.dto.JobRequestDTO;
import com.themore.moamoatown.job.dto.JobRequestResponseDTO;
import com.themore.moamoatown.job.dto.JobResponseDTO;
import com.themore.moamoatown.job.mapper.JobMapper;
import com.themore.moamoatown.job.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * JOB 관련 비즈니스 로직을 처리하는 서비스 구현체 클래스.
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
 * 2024.08.26   임재성        역할 요청 기능 추가
 * </pre>
 */
@Log4j
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobMapper jobMapper;

    @Override
    public List<JobResponseDTO> getJobsByTownId(Long townId) {
        log.info("타운 ID: " + townId + "에 대한 JOB 목록 조회 중");

        return jobMapper.findJobsByTownId(townId);
    }
    @Override
    public JobRequestResponseDTO requestJob(JobRequestDTO jobRequestDTO) {
        log.info("역할 요청 처리 중 - Job ID: " + jobRequestDTO.getJobId() + ", Member ID: " + jobRequestDTO.getMemberId());

        jobMapper.insertJobRequest(jobRequestDTO);

        return JobRequestResponseDTO.builder()
                .message("역할 요청이 성공적으로 처리되었습니다.")
                .build();
    }
}
