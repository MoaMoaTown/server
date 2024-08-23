package com.themore.moamoatown.town.service;

import com.themore.moamoatown.town.dto.TownCreateRequestDTO;
import com.themore.moamoatown.town.dto.TownCreateResponseDTO;
import com.themore.moamoatown.town.mapper.TownMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

/**
 * 타운 서비스 구현체
 * @author 임원정
 * @since 2024.08.23
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.23  	임원정        최초 생성
 * </pre>
 */

@Log4j
@Service
@AllArgsConstructor
public class TownServiceImpl implements TownService {
    private TownMapper townMapper;

    public TownCreateResponseDTO createTown(TownCreateRequestDTO requestDTO, Long memberId) {
        // 고유한 타운 코드 생성
        String townCode;
        do {
            townCode = TownCodeGenerator.generateTownCode();
        } while (townMapper.checkTownCodeExists(townCode));
        // 클라이언트로부터 받은 요청 데이터에 서버에서 필요한 추가 데이터 설정
        TownCreateRequestDTO townCreateRequestDTO = TownCreateRequestDTO.builder()
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .payCycle(requestDTO.getPayCycle())
                .townCode(townCode)  // 서버에서 생성한 타운 코드
                .build();

        // 타운 데이터 삽입 (MyBatis가 townId를 DTO에 매핑)
        townMapper.insertTown(townCreateRequestDTO);

        // MyBatis가 매핑한 townId를 사용
        Long townId = townCreateRequestDTO.getTownId();

        // 회원 테이블에 townId 업데이트
        townMapper.updateMemberTownId(townId, memberId);

        // 타운 생성 결과를 ResponseDTO로 반환
        return TownCreateResponseDTO.builder()
                .townId(townId)
                .townCode(townCode)
                .build();
    }
}
