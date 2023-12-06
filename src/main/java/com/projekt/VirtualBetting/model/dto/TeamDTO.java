package com.projekt.VirtualBetting.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
    private Long teamId;
    private String name;
    private Long leagueId;
}
