package com.themore.moamoatown.notification.controller;

import com.themore.moamoatown.common.annotation.MemberId;
import com.themore.moamoatown.notification.dto.NotificationDTO;
import com.themore.moamoatown.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

/**
 * 알림 컨트롤러
 * @author 임원정
 * @since 2024.08.28
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.28  	임원정        최초 생성
 * 2024.08.28   임원정        알림 구독, 알림 내역 조회 추가
 * </pre>
 */

@Log4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/notification",
        produces = "application/json; charset=UTF-8")
public class NotificationController {
    private final NotificationService notificationService;

    /**
     * 알림 구독
     * @param memberId
     * @return
     */
    @GetMapping(value="/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@MemberId Long memberId) {
        log.info("회원 id: "+memberId+"가 알림을 구독 하였습니다.");
        return notificationService.subscribe(memberId);
    }

    /**
     * 알림 내역 조회
     * @param memberId
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<NotificationDTO>> getNotifications(@MemberId Long memberId) {
        return ResponseEntity.ok(notificationService.getNotifications(memberId));
    }
}
