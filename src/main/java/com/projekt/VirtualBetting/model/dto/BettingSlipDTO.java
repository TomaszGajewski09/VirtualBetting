package com.projekt.VirtualBetting.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BettingSlipDTO {
    private Long bettingSlipId;
    private Long userId;
    private String status; // WIN, LOSE, PAID
    private String currency; // PLN, USD, EUR, GBP, CHF, JPY
    private BigDecimal wageredAmount;
    private BigDecimal possiblePayoutAmount;
    private List<Long> transactionsIds;
    private List<BettingOptionDTO> bettingOptions;
}
