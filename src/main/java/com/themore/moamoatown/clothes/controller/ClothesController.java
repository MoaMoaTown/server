package com.themore.moamoatown.clothes.controller;


import com.themore.moamoatown.clothes.dto.GetClothesResponseDTO;
import com.themore.moamoatown.clothes.dto.PostClothesPurchaseRequestDTO;
import com.themore.moamoatown.clothes.dto.PostClothesPurchaseResponseDTO;
import com.themore.moamoatown.clothes.service.ClothesService;
import com.themore.moamoatown.common.annotation.MemberId;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    /**
     * 옷 구매 후 클로젯에 추가
     * @param requestDTO
     * @param memberId 세션에서 가져온 멤버 아이디
     * @return
     * @throws Exception
     */
    @PostMapping("/purchase")
    public ResponseEntity<PostClothesPurchaseResponseDTO> purchaseClothes(
            @RequestBody PostClothesPurchaseRequestDTO requestDTO,
//            @MemberId Long memberId  테스트용으로 requestParam사용
            @RequestParam Long memberId // @MemberId 대신 @RequestParam 사용

    ) throws Exception {
        log.info("옷 ID: " + requestDTO.getClothesId() + "에 대한 구매 요청 처리 중");

        // memberId를 DTO에 설정
        PostClothesPurchaseResponseDTO response = clothesService.purchaseClothes(requestDTO, memberId);
        log.info("구매 완료 - " + response.getMessage());

        return ResponseEntity.ok(response);
    }
}


