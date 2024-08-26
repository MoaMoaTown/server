package com.themore.moamoatown.clothes.dto;
import lombok.*;

/**
 * 옷 구매 요청을 담고 있는 DTO 클래스.
 *
 * 구매하려는 옷의 ID를 포함하고 있습니다.
 *
 * @author 임재성
 * @since 2024.08.25
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.25  	임재성        최초 생성
 * </pre>
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ClothesPurchaseRequestDTO {
    private Long clothId;
}

