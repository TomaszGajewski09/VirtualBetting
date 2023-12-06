package com.projekt.VirtualBetting.api.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamAPI {

    @JsonProperty("team_key")
    private Long teamId;

    @JsonProperty("team_name")
    private String teamName;

    @JsonProperty("team_country")
    private String country;

    private Long leagueId;
}
