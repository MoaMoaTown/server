package com.themore.moamoatown.invest.service;

import com.themore.moamoatown.clothes.dto.ClothesResponseDTO;
import com.themore.moamoatown.clothes.service.ClothesService;
import com.themore.moamoatown.common.exception.CustomException;
import com.themore.moamoatown.invest.dto.*;
import com.themore.moamoatown.invest.mapper.InvestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.themore.moamoatown.common.exception.ErrorCode.*;

import java.math.BigDecimal;
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
 * 2024.08.27   임재성        오늘 가격과 힌트 가져오기
 * 2024.08.28   임재성        매수하기
 * 2024.08.28   임재성        매도하기
 * 2024.08.30   임재성        오늘의 투자 처리
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
    /**
     * 매수하기
     * @param
     * @return BuyInvestResponseDTO
     */
    @Transactional
    @Override
    public BuyInvestResponseDTO buyMemberInvest(Long memberId, BuyInvestRequestDTO buyInvestRequestDTO) {
        log.info("updateMemberInvest 호출됨 - 회원 ID: {}, 유형 ID: {}, 구매 수량: {}"+ memberId+ buyInvestRequestDTO.getTypeId()+ buyInvestRequestDTO.getPurchaseAmount());

        BuyInvestInternalRequestDTO internalRequestDTO = BuyInvestInternalRequestDTO.builder()
                .memberId(memberId)
                .typeId(buyInvestRequestDTO.getTypeId())
                .purchaseAmount(buyInvestRequestDTO.getPurchaseAmount())
                .build();

        log.info("InvestMapper의 updateMemberInvest 호출 - 요청 데이터: {}"+ internalRequestDTO);

        investMapper.buyMemberInvest(internalRequestDTO);


        BigDecimal result = internalRequestDTO.getResult(); // 프로시저 실행 후 결과 가져오기
        log.info("InvestMapper의 결과 값: {}"+ result);

        // 결과가 1이 아니면 예외 발생
        if (result == null || result.intValue() < 1) {
            log.info("실패 결과 값: {}"+result);
            if (result != null) {
                switch (result.intValue()) {
                    case -2:
                        log.info("Result: -2, 이유: 잔액이 부족합니다.");
                        throw new CustomException(INSUFFICIENT_BALANCE);
                    case -3:
                        log.info("Result: -3, 이유: 투자 데이터를 찾을 수 없습니다.");
                        throw new CustomException(NO_INVESTMENT_DATA_FOUND);
                    case -4:
                        log.info("Result: -4, 이유: 투자 정보 업데이트 실패.");
                        throw new CustomException(INVESTMENT_UPDATE_FAILED);
                    case -5:
                        log.info("Result: -5, 이유: 계좌 거래 내역 추가 실패.");
                        throw new CustomException(ACCOUNT_TRANSACTION_FAILED);
                    case -6:
                    default:
                        log.info("Result: -6, 이유: 알 수 없는 투자 관련 오류.");
                        throw new CustomException(UNKNOWN_INVESTMENT_ERROR);
                }
            } else {
                log.info("결과 값이 null입니다. 알 수 없는 오류 발생.");
                throw new CustomException(UNKNOWN_INVESTMENT_ERROR);
            }
        }

        log.info("투자 업데이트 성공. 성공 결과 값: {}"+ result);
        return BuyInvestResponseDTO.builder()
                .message("매수가 성공적으로 완료되었습니다.")
                .build();
    }

    /**
     * 매도하기
     * @param
     * @return SellInvestResponseDTO
     */
    @Transactional
    @Override
    public SellInvestResponseDTO sellMemberInvest(Long memberId, SellInvestRequestDTO sellInvestRequestDTO) {
        log.info("sellMemberInvest 호출됨 - 회원 ID: {}, 유형 ID: {}, 판매 수량: {}"+ memberId+ sellInvestRequestDTO.getTypeId()+ sellInvestRequestDTO.getSellAmount());

        SellInvestInternalRequestDTO internalRequestDTO = SellInvestInternalRequestDTO.builder()
                .memberId(memberId)
                .typeId(sellInvestRequestDTO.getTypeId())
                .sellAmount(sellInvestRequestDTO.getSellAmount())
                .build();

        log.info("InvestMapper의 sellMemberInvest 호출 - 요청 데이터: {}"+ internalRequestDTO);

        investMapper.sellMemberInvest(internalRequestDTO);


        BigDecimal result = internalRequestDTO.getResult(); // 프로시저 실행 후 결과 가져오기
        log.info("InvestMapper의 결과 값: {}"+ result);

        // 결과가 1이 아니면 예외 발생
        if (result == null || result.intValue() < 1) {
            log.info("실패 결과 값: {}"+result);
            if (result != null) {
                switch (result.intValue()) {
                    case -7:
                        log.info("Result: -7, 이유: 보유수량이 부족합니다.");
                        throw new CustomException(SELL_AMOUNT_ERROR);
                    case -3:
                        log.info("Result: -3, 이유: 투자 데이터를 찾을 수 없습니다.");
                        throw new CustomException(NO_INVESTMENT_DATA_FOUND);
                    case -4:
                        log.info("Result: -4, 이유: 투자 정보 업데이트 실패.");
                        throw new CustomException(INVESTMENT_UPDATE_FAILED);
                    case -5:
                        log.info("Result: -5, 이유: 계좌 거래 내역 추가 실패.");
                        throw new CustomException(ACCOUNT_TRANSACTION_FAILED);
                    case -6:
                    default:
                        log.info("Result: -6, 이유: 알 수 없는 투자 관련 오류.");
                        throw new CustomException(SELL_ERROR);
                }
            } else {
                log.info("결과 값이 null입니다. 알 수 없는 오류 발생.");
                throw new CustomException(SELL_ERROR);
            }
        }

        log.info("투자 업데이트 성공. 성공 결과 값: {}"+ result);
        return SellInvestResponseDTO.builder()
                .message("매도가 성공적으로 완료되었습니다.")
                .build();
    }






    /**
     * 오늘의 투자 처리
     * 매일 자정에 프로시저를 호출하여 INVEST_SCHEDULER에서 어제와 다른 HINT를 가진
     * 데이터를 선택하고 investment 테이블에 삽입합니다.
     */
    @Transactional
    @Override
    public void processTodayInvestment() {
        log.info("processTodayInvestment 프로시저 호출 시작");

        try {
            // MyBatis 매퍼를 통해 Oracle 프로시저 호출
            investMapper.callProcessTodayInvestmentProcedure();
            log.info("processTodayInvestment 프로시저 호출 완료");
        } catch (Exception e) {
            log.error("오늘의 투자 처리 중 오류 발생", e);
            throw e; // 필요시 예외 처리를 더 구체화할 수 있습니다.
        }
    }
}

