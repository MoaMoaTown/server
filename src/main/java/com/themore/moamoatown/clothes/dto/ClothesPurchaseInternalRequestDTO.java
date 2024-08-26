package com.themore.moamoatown.clothes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 옷 구매 요청을 담고 있는 내부 DTO 클래스.
 *
 * 구매하려는 옷의 ID와 memberId, 프로시저리턴값을 위한 result를 포함하고 있습니다.
 *
 * @author 임재성
 * @since 2024.08.26
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.26  	임재성        최초 생성
 * </pre>
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ClothesPurchaseInternalRequestDTO {
    private Long memberId;
    private Long clothId;
    private BigDecimal result; // 프로시저의 결과 값을 받는 필드
}
