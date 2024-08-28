    package com.themore.moamoatown.invest.dto;

    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Getter;
    import lombok.NoArgsConstructor;

    import java.math.BigDecimal;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public class BuyInvestInternalRequestDTO {
        private Long memberId;
        private int typeId;
        private int purchaseAmount;
        private BigDecimal result; // 프로시저의 결과 값을 받는 필드

    }
