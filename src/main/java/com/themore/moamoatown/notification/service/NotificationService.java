package com.themore.moamoatown.notification.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * 알림 서비스
 * @author 임원정
 * @since 2024.08.28
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.28  	임원정        최초 생성
 * 2024.08.28   임원정        알림 구독 추가
 * </pre>
 */

public interface NotificationService {
    // 알림 구독
    SseEmitter subscribe(Long memberId);
}
