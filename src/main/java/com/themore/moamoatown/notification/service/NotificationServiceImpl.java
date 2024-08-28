package com.themore.moamoatown.notification.service;

import com.themore.moamoatown.notification.dto.NotificationInsertDTO;
import com.themore.moamoatown.notification.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
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
 * 2024.08.28   임원정        알림 구독, 회원 알림 전송 관련 메소드 추가
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
            log.info("SSE 연결 timeout, 회원아이디: " + memberId);
            sseEmitters.remove(memberId);
        });
        return emitter;
    }

    /**
     * 회원 알림 전송 메소드
     * @param memberId
     * @param content
     */
    @Override
    @Transactional
    public void notifyMember(Long memberId, String content, String eventType) {
        NotificationInsertDTO insertDTO = NotificationInsertDTO.builder()
                .memberId(memberId)
                .content(content)
                .build();
        // 알림 저장
        notificationMapper.insertNotification(insertDTO);
        // 실시간 알림 전송
        sendNotification(memberId, content, eventType);
    }

    /**
     * 알림 전송
     *
     * @param memberId
     * @param content
     * @param eventType
     */
    public void sendNotification(Long memberId, String content, String eventType) {
        SseEmitter emitter = sseEmitters.get(memberId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name(eventType)
                        .data(content));
            } catch (IOException e) {
                log.error("알림을 보내는데 실패하였습니다.", e);
                sseEmitters.remove(memberId);
            }
        }
    }
}
