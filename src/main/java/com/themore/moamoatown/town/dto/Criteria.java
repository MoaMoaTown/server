package com.themore.moamoatown.town.dto;

import lombok.*;

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