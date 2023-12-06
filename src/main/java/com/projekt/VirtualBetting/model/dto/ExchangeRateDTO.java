package com.projekt.VirtualBetting.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateDTO {
    private Long baseCodeId;
    private String baseCode;
    private LocalDate updateDate;
    private BigDecimal pln;
    private BigDecimal usd;
    private BigDecimal eur;
    private BigDecimal gbp;
    private BigDecimal chf;
    private BigDecimal jpy;
}
