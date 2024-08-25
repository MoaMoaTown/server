package com.themore.moamoatown.job.mapper;

import com.themore.moamoatown.job.dto.JobResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * JOB 관련 데이터베이스 작업을 처리하는 매퍼 인터페이스.
 * MyBatis를 사용하여 데이터베이스와 상호작용합니다.
 *
 * @author 임재성
 * @since 2024.08.26
 * @version 1.0
 */
@Mapper
public interface JobMapper {
    /**
     * 타운 ID에 따른 JOB 목록 조회
     *
     * @param townId 조회할 타운 ID
     * @return 해당 타운에 속한 JOB 목록
     */
    List<JobResponseDTO> findJobsByTownId(@Param("townId") Long townId);
}
