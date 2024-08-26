package com.themore.moamoatown.clothes.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * 옷 목록 조회 응답 DTO 클래스.
 *
 * 옷에 대한 정보를 담고 있습니다.
 *
 * @author 임재성
 * @since 2024.08.24
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.24  	임재성        최초 생성
 * </pre>
 */
@Builder
@Getter
public class ClothesResponseDTO {
    private Long clothId;
    private String brand;
    private String name;
    private Long price;
    private Long type;
    private String imgUrl;
}

