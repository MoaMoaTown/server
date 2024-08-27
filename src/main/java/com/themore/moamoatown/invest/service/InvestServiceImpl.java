package com.themore.moamoatown.invest.service;

import com.themore.moamoatown.clothes.dto.ClothesResponseDTO;
import com.themore.moamoatown.clothes.service.ClothesService;
import com.themore.moamoatown.invest.dto.AverageResponseDTO;
import com.themore.moamoatown.invest.dto.TodayPriceResponseDTO;
import com.themore.moamoatown.invest.dto.YesterdayPriceResponseDTO;
import com.themore.moamoatown.invest.mapper.InvestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 투자 관련 비즈니스 로직을 처리하는 서비스 구현체 클래스.
 * 이 클래스는 {@link ClothesService} 인터페이스를 구현합니다.
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
 * 2024.08.27   임재성        어제 가격 조회
 * 2024.08.27   임재성       오늘 가격과 힌트 가져오기
 * </pre>
 */
@Log4j
@Service
@RequiredArgsConstructor
public class InvestServiceImpl implements InvestService{
    private final InvestMapper investMapper;

    /**
     * 흰디의 내일 보유 몸무게, 걸음 수 평단가 조회
     * @param memberId 회원 아이디
     * @return AverageResponseDTO
     */
    @Transactional(readOnly = true)
    @Override
    public List<AverageResponseDTO> getAverageWeightAndStep(Long memberId){
        return investMapper.getAverageWeightAndStep(memberId);
    }

    /**
     * 어제 흰디의 몸무게,걸음 수 가격과 힌트 조회
     * @param
     * @return YesterdayPriceResponseDTO
     */
    @Transactional(readOnly = true)
    @Override
    public List<YesterdayPriceResponseDTO> getYesterdayPrice(){
        return investMapper.getYesterdayPrice();
    }


    /**
     * 오늘 흰디의 몸무게,걸음 수 가격과 힌트 조회
     * @param
     * @return TodayPriceResponseDTO
     */
    @Transactional(readOnly = true)
    @Override
    public List<TodayPriceResponseDTO> getTodayPrice(){
        return investMapper.getTodayPrice();
    }
}
