package com.themore.moamoatown.clothes.dto;

import lombok.*;

/**
 * 옷 구매 결과 메시지를 담고 있는 DTO 클래스.
 *
 * 옷 구매 결과를 나타내는 메시지를 포함하고 있습니다.
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
public class ClothesPurchaseResponseDTO {
    private String message;
}

