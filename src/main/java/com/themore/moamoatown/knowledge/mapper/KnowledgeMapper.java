package com.themore.moamoatown.knowledge.mapper;

import com.themore.moamoatown.knowledge.dto.KnowledgeListResponseDTO;
import com.themore.moamoatown.knowledge.dto.KnowledgeResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 지식 매퍼 인터페이스
 * @author 이주현
 * @since 2024.08.26
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.26  이주현        최초 생성
 * 2024.08.26  이주현        지식 리스트 조회 기능 추가
 * </pre>
 */

@Mapper
public interface KnowledgeMapper {
    // 지식 상세 조회
    KnowledgeResponseDTO findById(Long knowledgeId);
    // 지식 리스트 조회
    List<KnowledgeListResponseDTO> findAllWithPaging(@Param("offset") int offset, @Param("size") int size);
}
