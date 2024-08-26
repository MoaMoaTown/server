package com.themore.moamoatown.town.service;

import com.themore.moamoatown.common.exception.CustomException;
import com.themore.moamoatown.town.dto.TownCreateInternalDTO;
import com.themore.moamoatown.town.dto.TownCreateRequestDTO;
import com.themore.moamoatown.town.dto.TownTaxResponseDTO;
import com.themore.moamoatown.town.mapper.TownMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.themore.moamoatown.common.exception.ErrorCode.TOWN_CREATE_FAILED;

/**
 * 타운 서비스 구현체
 * @author 임원정
 * @since 2024.08.23
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.23  	임원정        최초 생성
 * 2024.08.23   임원정        타운 만들기 추가
 * 2024.08.24   임원정        타운 만들기 메소드 수정
 * 2024.08.26   임원정        타운 세금 현황 조회 추가
 * </pre>
 */

@Log4j
@Service
@RequiredArgsConstructor
public class TownServiceImpl implements TownService {
    private final TownMapper townMapper;

    /**
     * 타운 만들기
     * @param requestDTO
     * @param memberId
     * @return
     */
    @Transactional
    public TownCreateInternalDTO createTown(TownCreateRequestDTO requestDTO, Long memberId) {
        // 고유한 타운 코드 생성
        String townCode;
        do {
            townCode = TownCodeGenerator.generateTownCode();
        } while (townMapper.selectIdByTownCode(townCode)!=null);

        TownCreateRequestDTO townCreateRequestDTO = TownCreateRequestDTO.builder()
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .payCycle(requestDTO.getPayCycle())
                .townCode(townCode)
                .build();

        // 생성한 타운 삽입
        if (townMapper.insertTown(townCreateRequestDTO) < 1) throw new CustomException(TOWN_CREATE_FAILED);
        Long townId = townCreateRequestDTO.getTownId();

        // 회원 테이블에 townId 업데이트
        if(townMapper.updateMember(townId, memberId) != 1) throw new CustomException(TOWN_CREATE_FAILED);

        return TownCreateInternalDTO.builder()
                .townId(townId)
                .townCode(townCode)
                .build();
    }

    /**
     * 타운 세금 현황 조회
     * @param townId
     * @return
     */
    @Override
    @Transactional
    public TownTaxResponseDTO getTotalTax(Long townId) {
        TownTaxResponseDTO townTaxResponseDTO = townMapper.selectTotalTaxByTownId(townId);
        return TownTaxResponseDTO.builder()
                .totalTax(townTaxResponseDTO.getTotalTax())
                .build();
    }

}
