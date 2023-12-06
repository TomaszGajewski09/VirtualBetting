package com.projekt.VirtualBetting.model.dto;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchDTO {
    private Long matchId;
    private LocalDateTime lastTimeUpdate;
    private Long leagueId;
    private String matchStatus;
    private Long homeTeamId;
    private Long awayTeamId;
    private int homeTeamScore;
    private int awayTeamScore;
    private LocalDate date;
    private LocalTime time;
    private Long oddsId;
//    private List<Long> bettingSlipIds;
//    private List<Long> goalscorersIds;
}
