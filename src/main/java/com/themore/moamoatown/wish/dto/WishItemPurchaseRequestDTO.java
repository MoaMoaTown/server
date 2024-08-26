package com.themore.moamoatown.wish.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 위시 상품 구매 요청 DTO 클래스.
 *
 * 구매하려는 위시 아이템의 ID를 포함합니다.
 *
 * @author 임재성
 * @since 2024.08.25
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.25   임재성        최초 생성
 * 2024.08.25   임재성        위시 상품 구매
 * </pre>
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WishItemPurchaseRequestDTO {
    private Long wishId;
    private Long memberId;
}
