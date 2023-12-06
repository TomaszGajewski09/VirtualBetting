package com.projekt.VirtualBetting.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OddsDTO {
    private Long oddsId;
    private Long matchId;
    private BigDecimal homeWin;
    private BigDecimal draw;
    private BigDecimal awayWin;
}
