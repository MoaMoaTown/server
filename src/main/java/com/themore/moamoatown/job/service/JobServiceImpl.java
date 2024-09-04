package com.themore.moamoatown.job.service;

import com.themore.moamoatown.common.exception.CustomException;
import com.themore.moamoatown.job.dto.JobApplyResponseDTO;
import com.themore.moamoatown.job.dto.JobRequestDTO;
import com.themore.moamoatown.job.dto.JobResponseDTO;
import com.themore.moamoatown.job.mapper.JobMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.themore.moamoatown.common.exception.ErrorCode.JOB_APPLY_INSERT_FAILED;
import static com.themore.moamoatown.common.exception.ErrorCode.NULL_COMMENTS;


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
//    @Transactional(readOnly = true)
//    @Override
//    public List<JobResponseDTO> getJobsByTownId(Long townId) {
//        log.info("타운 ID: " + townId + "에 대한 JOB 목록 조회 중");
//
//        return jobMapper.findJobsByTownId(townId);
//    }
    @Transactional(readOnly = true)
    @Override
    public List<JobResponseDTO> getJobsByTownId(Long townId, int page, int size) {
        log.info("타운 ID: " + townId + "에 대한 JOB 목록 조회 중 (페이지: " + page + ", 사이즈: " + size + ")");
        int offset = page * size;

        return jobMapper.findJobsByTownId(townId, offset, size);
    }
    /**
     * 주어진 요청 정보를 바탕으로 역할 요청을 처리합니다.
     *
     * @param jobRequestDTO 역할 요청 정보가 담긴 DTO
     * @return 역할 요청 결과를 나타내는 DTO
     * @throws CustomException 역할 요청 실패 시 예외 발생
     */
//    @Transactional
//    @Override
//    public JobApplyResponseDTO requestJob(JobRequestDTO jobRequestDTO) {
//        log.info("역할 요청 처리 중 - Job ID: " + jobRequestDTO.getJobId() + ", Member ID: " + jobRequestDTO.getMemberId());
//
//        if(1 > jobMapper.insertJobRequest(jobRequestDTO)) throw new CustomException(JOB_APPLY_INSERT_FAILED);
//
//        return JobApplyResponseDTO.builder()
//                .message("역할 요청이 성공적으로 처리되었습니다.")
//                .build();
//    }
    @Transactional
    @Override
    public JobApplyResponseDTO requestJob(JobRequestDTO jobRequestDTO) {
        log.info("역할 요청 처리 중 - Job ID: " + jobRequestDTO.getJobId() + ", Member ID: " + jobRequestDTO.getMemberId());

        try {
            if (jobMapper.insertJobRequest(jobRequestDTO) < 1) {
                throw new CustomException(JOB_APPLY_INSERT_FAILED);
            }
        } catch (DataIntegrityViolationException e) {
            // ORA-01400 오류를 잡아내어 커스텀 메시지를 반환
            throw new CustomException(NULL_COMMENTS);
        }

        return JobApplyResponseDTO.builder()
                .message("역할 요청이 성공했습니다.")
                .build();
    }

}
