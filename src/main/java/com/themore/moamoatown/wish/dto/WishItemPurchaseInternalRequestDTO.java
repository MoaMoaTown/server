package com.themore.moamoatown.wish.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
/**
 * 위시 상품 구매 요청 내부 DTO 클래스.
 *
 * 구매하려는 위시 아이템의 ID,멤버 ID, 프로시저 리턴값을 받기위한 result를 포함합니다.
 *
 * @author 임재성
 * @since 2024.08.26
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.26   임재성        최초 생성
 * </pre>
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WishItemPurchaseInternalRequestDTO {
    private Long memberId;
    private Long wishId;
    private BigDecimal result; // 프로시저의 결과 값을 받는 필드

}
