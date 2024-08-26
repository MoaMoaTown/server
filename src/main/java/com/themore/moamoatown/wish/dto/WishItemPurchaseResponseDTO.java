package com.themore.moamoatown.wish.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 위시 상품 구매 응답 DTO 클래스.
 *
 * 구매 결과를 나타내는 메시지를 포함합니다.
 *
 * @author 임재성
 * @since 2024.08.25
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.25   임재성        최초 생성
 * 2024.08.25   임재성        위시 상품 구매 응답
 * </pre>
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WishItemPurchaseResponseDTO {
    private String message;
}
