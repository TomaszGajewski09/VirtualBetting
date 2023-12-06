package com.projekt.VirtualBetting.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long transactionId;
    private Long bettingSlipId;
    private Long walletId;
    private String type; // DEPOSIT, WITHDRAWAL, WIN, LOSS, BET_PLACEMENT, BET_PAYOUT
    private BigDecimal amount;
    private String currency; // PLN, USD, EUR, GBP, CHF, JPY
    private BigDecimal beforeTransaction;
    private LocalDateTime transactionDateTime;
    private String description;
}
