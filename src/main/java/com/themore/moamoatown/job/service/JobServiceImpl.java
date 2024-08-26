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
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.26  	임재성        최초 생성
 * 2024.08.26   임재성        역할 리스트 조회 기능 추가
 * 2024.08.26   임재성        역할 리스트 조회 메서드 수정
 * 2024.08.26   임재성        역할 요청 기능 추가
 * 2024.08.26   임원정        getJobRequests, createJob, allowJobRequest 추가
 * </pre>
 */
@Log4j
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobMapper jobMapper;
    /**
     * 타운 ID를 통해 JOB 리스트를 조회합니다.
     * @param townId 타운 ID
     * @return JOB 리스트
     */
    @Transactional(readOnly = true)
    @Override
    public List<JobResponseDTO> getJobsByTownId(Long townId) {
        log.info("타운 ID: " + townId + "에 대한 JOB 목록 조회 중");

        return jobMapper.findJobsByTownId(townId);
    }
    /**
     * 주어진 요청 정보를 바탕으로 역할 요청을 처리합니다.
     *
     * @param jobRequestDTO 역할 요청 정보가 담긴 DTO
     * @return 역할 요청 결과를 나타내는 DTO
     * @throws CustomException 역할 요청 실패 시 예외 발생
     */
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
        if(jobMapper.insertJob(jobCreateRequestDTO) != 1) throw new CustomException(JOB_CREATE_FAILED);
    }

    /**
     * 역할 선정
     * @param jobRequestId
     */
    @Override
    @Transactional
    public void allowJobRequest(Long jobRequestId) {
        if(jobMapper.updateJobRequestAllowed(jobRequestId) != 1) throw new CustomException(JOB_REQUEST_ALLOW_FAILED);
    }
}
