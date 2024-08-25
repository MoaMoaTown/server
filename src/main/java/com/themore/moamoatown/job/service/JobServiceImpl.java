package com.themore.moamoatown.job.service.impl;

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
}
