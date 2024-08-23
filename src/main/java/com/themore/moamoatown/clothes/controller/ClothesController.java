package com.themore.moamoatown.clothes.controller;


import com.themore.moamoatown.clothes.dto.GetClothesResponseDTO;
import com.themore.moamoatown.clothes.service.ClothesService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/clothes", produces= MediaType.APPLICATION_JSON_VALUE)
@Log4j
@AllArgsConstructor
public class ClothesController {

    @Autowired
    private ClothesService clothesService;
    /**
     * 백화점 옷 조회
     * @param
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    public ResponseEntity<List<GetClothesResponseDTO>> getClothesListWithPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size
    ) {
        log.info("Fetching clothes list with pagination - Page: " + page + ", Size: " + size);

        List<GetClothesResponseDTO> response = clothesService.getClothesListWithPaging(page,size);
        log.info("Fetched " + response.size() + " clothes items.");

        return ResponseEntity.ok(response);
    }

}
