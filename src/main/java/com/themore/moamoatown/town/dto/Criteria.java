package com.themore.moamoatown.town.dto;

import lombok.*;

/**
 * 페이지 Criteria
 * @author 임원정
 * @since 2024.09.04
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.09.04  	임원정       최초 생성
 * </pre>
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Criteria {
    private int page = 1;
    private int size = 10;

    public int getOffset() {
        return (page - 1) * size;
    }
}