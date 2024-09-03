package com.themore.moamoatown.wish.controller;

import com.themore.moamoatown.common.annotation.Auth;
import com.themore.moamoatown.common.annotation.MemberId;
import com.themore.moamoatown.common.annotation.TownId;
import com.themore.moamoatown.wish.dto.WishItemPurchaseRequestDTO;
import com.themore.moamoatown.wish.dto.WishItemPurchaseResponseDTO;
import com.themore.moamoatown.wish.dto.WishItemResponseDTO;
import com.themore.moamoatown.wish.service.WishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 위시 상품 관련 API를 제공하는 컨트롤러 클래스.
 * @author 임재성
 * @since 2024.08.25
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.25  	임재성        최초 생성
 * 2024.08.25   임재성        위시 상품 조회 기능 추가
 * 2024.08.25   임재성        위시 상품 구매 기능 추가
 * 2024.08.26   임재성        위시 상품 구매 메소드 수정
 * </pre>
 */
@Auth(role = Auth.Role.CITIZEN)
@RestController
@RequestMapping(value="/wish", produces = "application/json; charset=UTF-8")
@Log4j
@RequiredArgsConstructor
public class WishController {
    private final WishService wishService;

    /**
     * 타운별 위시 아이템 목록 조회
     *
     * @param townId 세션에서 가져온 타운 ID
     * @return 위시 아이템 목록
     */
//    @GetMapping("/wishlist")
//    public ResponseEntity<List<WishItemResponseDTO>> getWishItemsByTown(
//            @TownId Long townId   //세션에서 가져올 시 이 방식 사용
//    ) {
//        log.info("타운 ID: " + townId + "의 위시 아이템 목록 조회 요청");
//
//        List<WishItemResponseDTO> response = wishService.getWishItemsByTown(townId);
//        log.info("조회된 위시 아이템 수: " + response.size());
//
//        return ResponseEntity.ok(response);
//    }
    @GetMapping("/wishlist")
    public ResponseEntity<List<WishItemResponseDTO>> getWishItemsByTown(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @TownId Long townId   // 세션에서 가져올 시 이 방식 사용
    ) {
        log.info("타운 ID: " + townId + "의 위시 아이템 목록 조회 요청 - 페이지: " + page + ", 사이즈: " + size);

        List<WishItemResponseDTO> response = wishService.getWishItemsByTown(townId, page, size);
        log.info("조회된 위시 아이템 수: " + response.size());

        return ResponseEntity.ok(response);
    }

    /**
     * 위시 아이템 구매
     *
     * @param requestDTO 구매하려는 위시 아이템의 ID와 멤버 ID를 포함한 요청 DTO.
     * @return 구매 결과 메시지를 포함한 응답 DTO.
     */
    @PostMapping("/wishlist/purchase")
    public ResponseEntity<WishItemPurchaseResponseDTO> purchaseWishItem(
            @RequestBody WishItemPurchaseRequestDTO requestDTO,
            @MemberId Long memberId, // @MemberId 어노테이션으로 세션에서 memberId를 가져옴
            @TownId Long townId
    ) {
        log.info("위시 아이템 ID: " + requestDTO.getWishId() + "에 대한 구매 요청 처리 중");

        // 세션에서 가져온 memberId와 기존의 wishId를 사용하여 DTO를 생성
        requestDTO = WishItemPurchaseRequestDTO.builder()
                .wishId(requestDTO.getWishId())
                .memberId(memberId)
                .build();

        // requestDTO로부터 wishId와 memberId를 이용해 처리
        WishItemPurchaseResponseDTO response = wishService.purchaseWishItem(requestDTO, townId);

        log.info("구매 완료 - " + response.getMessage());

        return ResponseEntity.ok(response);
    }
}
