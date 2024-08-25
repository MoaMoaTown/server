package com.themore.moamoatown.town.service;

import com.themore.moamoatown.town.dto.TownCreateInternalDTO;
import com.themore.moamoatown.town.dto.TownCreateRequestDTO;

/**
 * 타운 서비스 인터페이스
 * @author 임원정
 * @since 2024.08.23
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.23  	임원정        최초 생성
 * 2024.08.23   임원정        타운 만들기 추가
 * </pre>
 */

public interface TownService {
    TownCreateInternalDTO createTown(TownCreateRequestDTO townCreateRequestDTO, Long memberId);
}
