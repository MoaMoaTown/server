package com.themore.moamoatown.knowledge.service;

import com.themore.moamoatown.knowledge.dto.KnowledgeListResponseDTO;
import com.themore.moamoatown.knowledge.dto.KnowledgeResponseDTO;

import java.util.List;

/**
 * 지식 서비스 인터페이스
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

public interface KnowledgeService {
    // 지식 상세 조회
    KnowledgeResponseDTO getKnowledgeById(Long knowledgeId);
    // 지식 리스트 조회
    List<KnowledgeListResponseDTO> getAllKnowledge(int page, int size);
}
