package com.projekt.VirtualBetting.api.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchAPI {
    @JsonProperty("match_id")
    private Long matchId;

    @JsonProperty("match_status")
    private String matchStatus;

    @JsonProperty("country_name")
    private String countryName;

    @JsonProperty("league_id")
    private Long leagueId;
    @JsonProperty("league_name")
    private String leagueName;
    @JsonProperty("league_year")
    private String leagueYear;


    @JsonProperty("match_hometeam_id")
    private Long homeTeamId;
    @JsonProperty("match_awayteam_id")
    private Long awayTeamId;

    @JsonProperty("match_hometeam_name")
    private String homeTeamName;
    @JsonProperty("match_awayteam_name")
    private String awayTeamName;

    @JsonProperty("match_hometeam_score")
    private int homeTeamScore;
    @JsonProperty("match_awayteam_score")
    private int awayTeamScore;



    @JsonProperty("match_date")
    private LocalDate matchDate;

    @JsonProperty("match_time")
    private LocalTime matchTime;

}

