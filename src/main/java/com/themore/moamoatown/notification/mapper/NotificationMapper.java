package com.themore.moamoatown.notification.mapper;

import com.themore.moamoatown.notification.dto.NotificationDTO;
import com.themore.moamoatown.notification.dto.NotificationInsertDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 알림 Mapper
 * @author 임원정
 * @since 2024.08.28
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.28  	임원정        최초 생성
 * 2024.08.28   임원정        insertNotification, findNotificationsByMemberId 메소드 추가
 * </pre>
 */

@Mapper
public interface NotificationMapper {
    // 알림 저장
    void insertNotification(NotificationInsertDTO notificationInsertDTO);
    // 알림 내역 조회
    List<NotificationDTO> findNotificationsByMemberId(Long memberId);
}