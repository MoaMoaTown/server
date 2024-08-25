package com.themore.moamoatown.knowledge.service;

import com.themore.moamoatown.common.exception.CustomException;
import com.themore.moamoatown.knowledge.dto.KnowledgeResponseDTO;
import com.themore.moamoatown.knowledge.mapper.KnowledgeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.themore.moamoatown.common.exception.ErrorCode.*;

/**
 * 지식 서비스 구현체
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

@Service
@RequiredArgsConstructor
public class KnowledgeServiceImpl implements KnowledgeService {
    private final KnowledgeMapper knowledgeMapper;

    /**
     * 지식 상세 정보 조회
     * @param knowledgeId 지식 ID
     * @return KnowledgeResponseDTO
     */
    @Override
    @Transactional(readOnly = true)
    public KnowledgeResponseDTO getKnowledgeById(Long knowledgeId) {
        KnowledgeResponseDTO knowledge = knowledgeMapper.findById(knowledgeId);
        if (knowledge == null) {
            throw new CustomException(KNOWLEDGE_NOT_FOUND);
        }
        return knowledge;
    }
}
