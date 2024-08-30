package com.themore.moamoatown.clothes.controller;


import com.themore.moamoatown.clothes.dto.*;
import com.themore.moamoatown.clothes.service.ClothesService;
import com.themore.moamoatown.common.annotation.Auth;
import com.themore.moamoatown.common.annotation.MemberId;
import com.themore.moamoatown.wish.dto.WishItemPurchaseRequestDTO;
import com.themore.moamoatown.wish.dto.WishItemPurchaseResponseDTO;
import com.themore.moamoatown.wish.dto.WishItemResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Auth(role = Auth.Role.CITIZEN)
@RestController
@RequestMapping(value="/clothes", produces = "application/json; charset=UTF-8")
@Log4j
@RequiredArgsConstructor
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
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "3") int size
    ) {
        log.info("Fetching clothes list");

        List<ClothesResponseDTO> response = clothesService.getClothesListWithPaging();
        log.info("Fetched " + response.size() + " clothes items.");

        return ResponseEntity.ok(response);
    }
    /**
     * 옷 구매 후 클로젯에 추가
     * @param memberId 세션에서 가져온 멤버 아이디
     * @return
     * @throws Exception
     */
    @PostMapping("/purchase")
    public ResponseEntity<ClothesPurchaseResponseDTO> purchaseClothes(
//            @RequestBody ClothesPurchaseRequestDTO requestDTO,
            @RequestBody Long ClothId,
            @MemberId Long memberId
    ) throws Exception {
//        log.info("옷 ID: " + requestDTO.getClothId() + "에 대한 구매 요청 처리 중");
        log.info("옷 ID:"+ ClothId);

        ClothesPurchaseResponseDTO response = clothesService.purchaseClothes(ClothId, memberId);
        log.info("구매 완료 - " + response.getMessage());

        return ResponseEntity.ok(response);
    }






}


