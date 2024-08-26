package com.themore.moamoatown.job.controller;

import com.themore.moamoatown.common.annotation.Auth;
import com.themore.moamoatown.common.annotation.MemberId;
import com.themore.moamoatown.common.annotation.TownId;
import com.themore.moamoatown.job.dto.*;
import com.themore.moamoatown.job.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * JOB 관련 API를 제공하는 컨트롤러 클래스.
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
 * 2024.08.26   임원정        타운 역할 신청 현황, 역할 만들기, 역할 선정 추가
 * </pre>
 */
@Auth(role = Auth.Role.CITIZEN)
@RestController
@RequestMapping(value="/jobs", produces = "application/json; charset=UTF-8")
@Log4j
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    /**
     * 타운 ID에 따른 JOB 목록 조회
     *
     *  세션에서 가져온 타운 ID.
     * @return 타운에 속한 JOB 목록을 담은 응답 DTO.
     */
    @GetMapping("/list")
    public ResponseEntity<List<JobResponseDTO>> getJobsByTownId(@TownId Long townId) {


        log.info("타운 ID: " + townId + "에 대한 JOB 목록 조회 요청 처리 중");

        List<JobResponseDTO> jobList = jobService.getJobsByTownId(townId);

        log.info("JOB 목록 조회 완료 - " + jobList.size() + "개 항목");

        return ResponseEntity.ok(jobList);
    }

    /**
     * 역할 요청 처리
     *
     * @param jobRequestDTO 역할 요청 정보
     * @param memberId 세션에서 가져온 멤버 ID
     * @return 요청 처리 결과 메시지를 담은 응답 DTO
     */
    @PostMapping("/apply")
    public ResponseEntity<JobApplyResponseDTO> requestJob(
            @RequestBody JobRequestDTO jobRequestDTO,
            @MemberId Long memberId // 세션에서 가져온 멤버 ID를 주입받음
    ) {
        log.info("역할 요청 - Job ID: " + jobRequestDTO.getJobId() + ", Member ID: " + memberId);

        // 세션에서 가져온 memberId를 DTO에 설정
        jobRequestDTO = JobRequestDTO.builder()
                .jobId(jobRequestDTO.getJobId())
                .memberId(memberId)
                .comments(jobRequestDTO.getComments())
                .build();

        JobApplyResponseDTO response = jobService.requestJob(jobRequestDTO);

        log.info("역할 요청 처리 완료 - " + response.getMessage());

        return ResponseEntity.ok(response);
    }

    /**
     * 타운 내 역할 신청 현황 조회
     * @param townId
     * @return
     */
    @Auth(role = Auth.Role.MAYER)
    @GetMapping("/requests")
    public ResponseEntity<List<JobRequestsResponseDTO>> getJobRequests(@TownId Long townId) {
        List<JobRequestsResponseDTO> response = jobService.getJobRequests(townId);
        return ResponseEntity.ok(response);
    }

    /**
     * 역할 만들기
     * @param requestDTO
     * @param townId
     * @return
     */
    @Auth(role = Auth.Role.MAYER)
    @PostMapping("/create")
    public ResponseEntity<String> createJob(@Valid @RequestBody JobCreateRequestDTO requestDTO, @TownId Long townId){
        jobService.createJob(requestDTO, townId);
        return ResponseEntity.ok("역할 생성이 완료 되었습니다.");
    }

    /**
     * 역할 요청 승인(역할 선정)
     * @param jobRequestId
     * @return
     */
    @Auth(role = Auth.Role.MAYER)
    @PatchMapping("/allow/{jobRequestId}")
    public ResponseEntity<String> allowJobRequest(@PathVariable Long jobRequestId) {
        jobService.allowJobRequest(jobRequestId);
        return ResponseEntity.ok("역할이 선정되었습니다.");
    }
}