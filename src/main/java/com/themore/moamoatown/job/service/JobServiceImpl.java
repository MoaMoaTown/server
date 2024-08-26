package com.themore.moamoatown.job.service;

import com.themore.moamoatown.common.exception.CustomException;
import com.themore.moamoatown.job.dto.*;
import com.themore.moamoatown.job.mapper.JobMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.themore.moamoatown.common.exception.ErrorCode.*;


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
 * 2024.08.26   임원정        타운 내 역할 신청 현황 조회 메소드 추가
 * </pre>
 */
@Log4j
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobMapper jobMapper;

    @Transactional(readOnly = true)
    @Override
    public List<JobResponseDTO> getJobsByTownId(Long townId) {
        log.info("타운 ID: " + townId + "에 대한 JOB 목록 조회 중");

        return jobMapper.findJobsByTownId(townId);
    }

    @Transactional
    @Override
    public JobApplyResponseDTO requestJob(JobRequestDTO jobRequestDTO) {
        log.info("역할 요청 처리 중 - Job ID: " + jobRequestDTO.getJobId() + ", Member ID: " + jobRequestDTO.getMemberId());

        if(1 > jobMapper.insertJobRequest(jobRequestDTO)) throw new CustomException(JOB_APPLY_INSERT_FAILED);

        return JobApplyResponseDTO.builder()
                .message("역할 요청이 성공적으로 처리되었습니다.")
                .build();
    }

    /**
     * 타운 내 역할 신청 현황 조회
     * @param townId
     * @return
     */
    @Override
    @Transactional
    public List<JobRequestsResponseDTO> getJobRequests(Long townId){

        return jobMapper.selectJobRequestByTownId(townId)
                .stream()
                .map(jobRequest -> JobRequestsResponseDTO.builder()
                        .jobRequestId(jobRequest.getJobRequestId())
                        .name(jobRequest.getName())
                        .comments(jobRequest.getComments())
                        .nickName(jobRequest.getNickName())
                        .allowYN(jobRequest.getAllowYN())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 역할 생성
     * @param requestDTO
     * @param townId
     */
    @Override
    @Transactional
    public void createJob (JobCreateRequestDTO requestDTO, Long townId){
        JobCreateRequestDTO jobCreateRequestDTO = JobCreateRequestDTO.builder()
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .pay(requestDTO.getPay())
                .townId(townId)
                .build();

        log.info(jobCreateRequestDTO.toString());
        if(jobMapper.insertJob(jobCreateRequestDTO) != 1) throw new CustomException(JOB_CREATE_FAILED);
    }
}
