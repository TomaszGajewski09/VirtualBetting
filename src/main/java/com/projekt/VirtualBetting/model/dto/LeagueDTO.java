package com.projekt.VirtualBetting.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeagueDTO {
    private Long leagueId;
    private String leagueName;
    private String countryName;
}
