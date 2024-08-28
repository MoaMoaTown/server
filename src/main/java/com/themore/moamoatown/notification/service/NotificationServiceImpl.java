package com.themore.moamoatown.notification.service;

import com.themore.moamoatown.notification.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 알림 서비스 구현체
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

@Log4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final ConcurrentMap<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
    private final NotificationMapper notificationMapper;

    // 타임아웃 설정 (5분)
    private static final Long TIMEOUT = 300_000L;

    /**
     * 알림 구독
     * @param memberId
     * @return
     */
    @Override
    public SseEmitter subscribe(Long memberId) {
        SseEmitter emitter = new SseEmitter(TIMEOUT);
        sseEmitters.put(memberId, emitter);

        emitter.onCompletion(() -> sseEmitters.remove(memberId));
        emitter.onTimeout(() -> {
            log.info("SSE connection timeout for memberId: " + memberId);
            sseEmitters.remove(memberId);
        });

        return emitter;
    }
}
