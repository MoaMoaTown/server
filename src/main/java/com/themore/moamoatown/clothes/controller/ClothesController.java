package com.themore.moamoatown.clothes.controller;


import com.themore.moamoatown.clothes.dto.ClothesResponseDTO;
import com.themore.moamoatown.clothes.dto.ClothesPurchaseRequestDTO;
import com.themore.moamoatown.clothes.dto.ClothesPurchaseResponseDTO;
import com.themore.moamoatown.clothes.service.ClothesService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * 옷 관련 API를 제공하는 컨트롤러 클래스.
 * @author 임재성
 * @since 2024.08.24
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.24  	임재성        최초 생성
 * 2024.08.24  	임재성        옷 조회 기능 추가
 * 2024.08.25  	임재성        옷 구매 기능 추가
 * </pre>
 */
@RestController
@RequestMapping(value="/clothes", produces = "application/json; charset=UTF-8")
@Log4j
@RequiredArgsConstructor
@Getter
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
    public ResponseEntity<List<ClothesResponseDTO>> getClothesListWithPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size
    ) {
        log.info("Fetching clothes list with pagination - Page: " + page + ", Size: " + size);

        List<ClothesResponseDTO> response = clothesService.getClothesListWithPaging(page,size);
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
    public ResponseEntity<ClothesPurchaseResponseDTO> purchaseClothes(
            @RequestBody ClothesPurchaseRequestDTO requestDTO,
//            @MemberId Long memberId  테스트용으로 requestParam사용
            @RequestParam Long memberId // @MemberId 대신 @RequestParam 사용

    ) throws Exception {
        log.info("옷 ID: " + requestDTO.getClothesId() + "에 대한 구매 요청 처리 중");

        // memberId를 DTO에 설정
        ClothesPurchaseResponseDTO response = clothesService.purchaseClothes(requestDTO, memberId);
        log.info("구매 완료 - " + response.getMessage());

        return ResponseEntity.ok(response);
    }
}


