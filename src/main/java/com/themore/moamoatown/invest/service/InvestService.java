package com.themore.moamoatown.invest.service;

import com.themore.moamoatown.invest.dto.*;

import java.util.List;

/**
 * 투자 관련 비즈니스 로직을 처리하는 서비스 인터페이스.
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
 * 2024.08.27   임재성        오늘 가격과 힌트 가져오기
 * 2024.08.28   임재성        매수하기
 * 2024.08.28   임재성        매도하기
 * </pre>
 */
public interface InvestService {
    List<AverageResponseDTO> getAverageWeightAndStep(Long memberId);

    List<YesterdayPriceResponseDTO> getYesterdayPrice();

    List<TodayPriceResponseDTO> getTodayPrice();

    BuyInvestResponseDTO buyMemberInvest(Long memberId, BuyInvestRequestDTO buyInvestRequestDTO);

    SellInvestResponseDTO sellMemberInvest(Long memberId, SellInvestRequestDTO sellInvestRequestDTO);

    void processTodayInvestment();
}

