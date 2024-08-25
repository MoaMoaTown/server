package com.themore.moamoatown.knowledge.mapper;

import com.themore.moamoatown.knowledge.dto.KnowledgeResponseDTO;
import org.apache.ibatis.annotations.Mapper;

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
 * </pre>
 */

@Mapper
public interface KnowledgeMapper {
    // 지식 상세 조회
    KnowledgeResponseDTO findById(Long knowledgeId);
}
