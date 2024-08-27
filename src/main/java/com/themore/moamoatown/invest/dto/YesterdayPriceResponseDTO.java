package com.themore.moamoatown.invest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;


/**
 * 흰디의 내일 어제 몸무게,걸음수 가격 조회 DTO 클래스.
 *
 *
 *
 * @author 임재성
 * @since 2024.08.27
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.27  	임재성        최초 생성
 * </pre>
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class YesterdayPriceResponseDTO {
    private Long type;
    private Long price;
    private String createdAt;
    private String hint;
}
