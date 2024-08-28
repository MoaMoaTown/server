package com.themore.moamoatown.notification.mapper;

import com.themore.moamoatown.notification.dto.NotificationInsertDTO;
import org.apache.ibatis.annotations.Mapper;

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
 * 2024.08.28   임원정        insertNotification 메소드 추가
 * </pre>
 */

@Mapper
public interface NotificationMapper {
    void insertNotification(NotificationInsertDTO notificationInsertDTO);
}
