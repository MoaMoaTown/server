package com.themore.moamoatown.common.scheduler;

import com.themore.moamoatown.invest.service.InvestService;
import com.themore.moamoatown.town.service.TownService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 오늘의 투자 스케줄러
 *
 * 이 스케줄러는 매일 자정에 InvestService의 오늘의 투자 메소드를 호출합니다.
 *
 * @author 임재성
 * @since 2024.08.30
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.30   임재성       최초 생성
 * </pre>
 */

@Component
@EnableScheduling
@RequiredArgsConstructor
public class TodayInvestScheduler {

    private final InvestService investService;

    @Scheduled(cron = "0 0 0 * * ?")  // 매일 자정에 실행
    //@Scheduled(cron = "0 * * * * ?")  // 매 분 0초에 실행_테스트용
    public void scheduleInvestmentProcessing() {
        investService.processTodayInvestment();
    }
}