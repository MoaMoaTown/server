package com.themore.moamoatown.invest.mapper;

import com.themore.moamoatown.invest.dto.AverageResponseDTO;
import com.themore.moamoatown.invest.dto.YesterdayPriceResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 투자 관련 데이터베이스 작업을 처리하는 매퍼 인터페이스.
 * MyBatis를 사용하여 데이터베이스와 상호작용합니다.
 *
 * @author 임재성
 * @since 2024.08.27
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.27  	임재성        최초 생성
 * 2024.08.27  	임재성        내 흰디의 몸무게 평단가 조회
 * 2024.08.27  	임재성        내 흰디의 걸음수 평단가 조회
 * </pre>
 */
@Mapper
public interface InvestMapper {
    List<AverageResponseDTO> getAverageWeightAndStep(Long memberId);

    List<YesterdayPriceResponseDTO> getYesterdayPrice();
}
