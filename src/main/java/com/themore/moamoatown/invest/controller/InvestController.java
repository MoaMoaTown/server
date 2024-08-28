package com.themore.moamoatown.invest.controller;

import com.themore.moamoatown.common.annotation.Auth;
import com.themore.moamoatown.common.annotation.MemberId;
import com.themore.moamoatown.common.annotation.TownId;
import com.themore.moamoatown.invest.dto.*;
import com.themore.moamoatown.invest.service.InvestService;
import com.themore.moamoatown.quest.dto.QuestResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 투자 관련 API를 제공하는 컨트롤러 클래스.
 * @author 임재성
 * @since 2024.08.27
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.27  	임재성        최초 생성
 * 2024.08.27  	임재성        내 흰디의 몸무게 평단가 조회
 * 2024.08.27  	임재성        내 흰디의 걸음수 평단가 조회
 * 2024.08.27   임재성        어제 가격 조회
 * 2024.08.27   임재성       오늘 가격과 힌트 가져오기
 * </pre>
 */
@Auth(role = Auth.Role.CITIZEN)
@RestController
@RequestMapping(value="/invest", produces = "application/json; charset=UTF-8")
@Log4j
@RequiredArgsConstructor
public class InvestController {
    @Autowired
    InvestService investService;
    /**
     * 내 흰디의 몸무게,걸음 수 평단가 조회
     * @param memberId
     * @return
     * @throws Exception
     */
    @GetMapping("/average")
    public ResponseEntity<List<AverageResponseDTO>> getAverageWeight(@MemberId Long memberId) {
        List<AverageResponseDTO> response = investService.getAverageWeightAndStep(memberId);
        return ResponseEntity.ok(response);
    }
    /**
     * 어제 흰디의 몸무게,걸음 수 가격 조회
     * @param
     * @return
     * @throws Exception
     */
    @GetMapping("/yesterday")
    public ResponseEntity<List<YesterdayPriceResponseDTO>> getYesterdayPrice() {
        List<YesterdayPriceResponseDTO> response = investService.getYesterdayPrice();
        return ResponseEntity.ok(response);
    }
    /**
     * 오늘 흰디의 몸무게,걸음 수 가격 조회
     * @param
     * @return
     * @throws Exception
     */
    @GetMapping("/today")
    public ResponseEntity<List<TodayPriceResponseDTO>> getTodayPrice() {
        List<TodayPriceResponseDTO> response = investService.getTodayPrice();
        return ResponseEntity.ok(response);
    }
    /**
     * 매수하기
     * @param memberId 세션에서 받아오는 회원 ID
     * @param buyInvestRequestDTO 구매 요청 데이터
     * @return ResponseEntity<BuyInvestResponseDTO>
     * @throws Exception
     */
    @PatchMapping("/buy")
    public ResponseEntity<BuyInvestResponseDTO> updateMemberInvest(
            @MemberId Long memberId,
            @RequestBody BuyInvestRequestDTO buyInvestRequestDTO) {
        BuyInvestResponseDTO response = investService.updateMemberInvest(memberId, buyInvestRequestDTO);
        return ResponseEntity.ok(response);
    }

}
