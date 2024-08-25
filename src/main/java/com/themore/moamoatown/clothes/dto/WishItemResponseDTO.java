package com.themore.moamoatown.clothes.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * 위시 아이템 응답 DTO 클래스.
 *
 * 위시 아이템에 대한 정보를 담고 있습니다.
 *
 * @author 임재성
 * @since 2024.08.27
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.25   임재성        최초 생성
 * 2024.08.25   임재성        위시 상품 조회
 * </pre>
 */
@Builder
@Getter
public class WishItemResponseDTO {
    private Long wishId;
    private Long townId;
    private String name;
    private Long price;
}
