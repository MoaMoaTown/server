package com.themore.moamoatown.knowledge.service;

import com.themore.moamoatown.common.exception.CustomException;
import com.themore.moamoatown.knowledge.dto.KnowledgeListResponseDTO;
import com.themore.moamoatown.knowledge.dto.KnowledgeResponseDTO;
import com.themore.moamoatown.knowledge.mapper.KnowledgeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
 * 2024.08.26  이주현        지식 리스트 조회 기능 추가
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

        return KnowledgeResponseDTO.builder()
                .title(knowledge.getTitle())
                .content(knowledge.getContent())
                .createdAt(knowledge.getCreatedAt())
                .build();
    }

    /**
     * 지식 리스트 조회
     * @param page 페이지 번호
     * @param size 페이지 당 항목 수
     * @return List<KnowledgeListResponseDTO>
     */
    @Override
    @Transactional(readOnly = true)
    public List<KnowledgeListResponseDTO> getAllKnowledge(int page, int size) {
        int offset = (page-1) * size;
        try {
            return knowledgeMapper.findAllWithPaging(offset, size)
                    .stream()
                    .map(knowledge -> KnowledgeListResponseDTO.builder()
                            .knowledgeId(knowledge.getKnowledgeId())
                            .title(knowledge.getTitle())
                            .content(knowledge.getContent())
                            .createdAt(knowledge.getCreatedAt())
                            .build())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomException(DATABASE_ERROR);
        }
    }
}
