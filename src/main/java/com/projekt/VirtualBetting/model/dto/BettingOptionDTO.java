package com.projekt.VirtualBetting.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class  BettingOptionDTO {
    private Long matchId;
    private String selectedOption; // HOME, DRAW, AWAY
}
