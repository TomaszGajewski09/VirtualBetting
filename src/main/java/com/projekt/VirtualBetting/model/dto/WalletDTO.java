package com.projekt.VirtualBetting.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletDTO {
    private Long walletId;
    private Long userId;
    private BigDecimal balance;
    private String currency; // PLN, USD, EUR, GBP, CHF, JPY
    private LocalDateTime lastTransactionTime;
    private List<Long> transactionIds;
}
