package com.themore.moamoatown.common.scheduler;

import com.themore.moamoatown.town.service.TownService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 급여 지급 스케줄러
 * @author 임원정
 * @since 2024.08.30
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.30  	임원정       최초 생성
 * </pre>
 */

@Component
@EnableScheduling
@RequiredArgsConstructor
public class PayrollScheduler {
    private final TownService townService;

    @Scheduled(cron = "0 0 0 * * ?")  // 매일 자정에 실행
    public void schedulePayrollProcessing() {
        townService.processPayroll();  // TownService의 급여 지급 메소드 호출
    }
}