package com.themore.moamoatown.clothes.dto;

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
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WishItemPurchaseRequestDTO {
    private Long wishId;
    private Long memberId;
}
