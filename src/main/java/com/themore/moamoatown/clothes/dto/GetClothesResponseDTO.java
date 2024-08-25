package com.themore.moamoatown.clothes.dto;

import lombok.Data;

@Data
public class GetClothesResponseDTO {
    private Long clothesId;
    private String brand;
    private String name;
    private Long price;
    private Long type;
    private String imgUrl;
}

