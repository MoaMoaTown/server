package com.themore.moamoatown.common.scheduler;

import com.themore.moamoatown.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 이자 지급 스케줄러
 * @author 이주현
 * @since 2024.09.06
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.09.06  	이주현       최초 생성
 * </pre>
 */

@Component
@EnableScheduling
@RequiredArgsConstructor
public class InterestScheduler {
    private final MemberService memberService;

    // 매일 자정에 실행
    @Scheduled(cron = "0 0 0 * * ?")
    public void executeInterestPayment() {
        memberService.processInterestPayment();
    }
}