package com.themore.moamoatown.invest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
/**
 * 매수하기 요청 내부 DTO 클래스.
 *
 * 구매하려는 상품의 typeId와 memberId, 프로시저리턴값을 위한 result를 포함하고 있습니다.
 *
 * @author 임재성
 * @since 2024.08.28
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.28  	임재성        최초 생성
 * </pre>
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BuyInvestInternalRequestDTO {
        private Long memberId;
        private int typeId;
        private int purchaseAmount;
        private BigDecimal result; // 프로시저의 결과 값을 받는 필드

}
