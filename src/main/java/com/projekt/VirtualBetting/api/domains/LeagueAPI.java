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
public class LeagueAPI {

    @JsonProperty("league_id")
    private Long leagueId;

    @JsonProperty("league_name")
    private String leagueName;

    @JsonProperty("country_id")
    private Long countryId;

    @JsonProperty("country_name")
    private String countryName;

}
