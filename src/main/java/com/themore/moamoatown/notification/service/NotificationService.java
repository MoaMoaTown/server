package com.themore.moamoatown.notification.service;

import com.themore.moamoatown.notification.dto.NotificationDTO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

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
 * 2024.08.28   임원정        알림 구독, 회원에게 알림 전송 추가
 * 2024.08.28   임원정        알림 내역 조회 추가
 * </pre>
 */

public interface NotificationService {
    // 알림 구독
    SseEmitter subscribe(Long memberId);
    // 회원에게 알림 전송
    SseEmitter notifyMember(Long memberId, String content);
    // 알림 내역 조회
    List<NotificationDTO> getNotifications(Long memberId);
}
