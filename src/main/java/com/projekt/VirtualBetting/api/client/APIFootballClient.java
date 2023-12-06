package com.projekt.VirtualBetting.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projekt.VirtualBetting.api.config.APIFootballConfig;
import com.projekt.VirtualBetting.api.domains.LeagueAPI;
import com.projekt.VirtualBetting.api.domains.MatchAPI;
import com.projekt.VirtualBetting.api.domains.OddsAPI;
import com.projekt.VirtualBetting.api.domains.TeamAPI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class APIFootballClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(APIFootballClient.class);
    // TODO: Sprawdź pobieranie danych w przyszłch terminach, szczególnie odds
    private final APIFootballConfig apiFootballConfig;
    private final RestTemplate restTemplate;

    private UriComponentsBuilder getDefaultUrlBuilder() {
        return UriComponentsBuilder.fromHttpUrl(apiFootballConfig.getApiUrl())
                .queryParam("APIkey", apiFootballConfig.getApiKey());
    }

    public List<LeagueAPI> getLeagues() {
        URI url = getDefaultUrlBuilder()
                .queryParam("action", "get_leagues")
                .build()
                .encode()
                .toUri();

        try {
            LOGGER.info("APIFootballClient - getLeagues() | " + "Initializing request: " + url);
            LeagueAPI[] leagues = restTemplate.getForObject(url, LeagueAPI[].class);
            return Arrays.asList(leagues);
        } catch (Exception e) {
            //TODO: Create exception
            LOGGER.error("APIFootballClient - getLeagues() | " + e.getMessage(), e);
            return null;
        }
    }

    public LeagueAPI getLeagueById(Long leagueId) {
        URI url = getDefaultUrlBuilder()
                .queryParam("action", "get_leagues")
                .queryParam("league_id", leagueId)
                .build()
                .encode()
                .toUri();

        try {
            LOGGER.info("APIFootballClient - getLeagueById() | " + "Initializing request: " + url);
            LeagueAPI[] leagues = restTemplate.getForObject(url, LeagueAPI[].class);
            return Arrays.stream(leagues)
                    .filter(l -> l.getLeagueId().equals(leagueId))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            //TODO: Create exception
            LOGGER.error("APIFootballClient - getLeagueById() | " + e.getMessage(), e);
            return null;
        }
    }


    public TeamAPI getTeamById(Long teamId) {
        URI url = getDefaultUrlBuilder()
                .queryParam("action", "get_teams")
//                .queryParam("league_id", leagueId)
                .queryParam("team_id", teamId)
                .build()
                .encode()
                .toUri();

        try {
            LOGGER.info("APIFootballClient - getTeamById() | " + "Initializing request: " + url);
            TeamAPI[] teamsAPI = restTemplate.getForObject(url, TeamAPI[].class);
            return Arrays.stream(teamsAPI)
                    .findFirst()
                    .orElse(null);

        } catch (Exception e) {
            //TODO: Create exception
            LOGGER.error("APIFootballClient - getTeamById() | " + e.getMessage(), e);
            return null;
        }
    }

    public List<TeamAPI> getTeamsByLeague(Long leagueId) {

        URI url = getDefaultUrlBuilder()
                .queryParam("action", "get_teams")
                .queryParam("league_id", leagueId)
                .build()
                .encode()
                .toUri();

        try {
            LOGGER.info("APIFootballClient - getTeamsByLeague() | " + "Initializing request: " + url);
            TeamAPI[] teamAPIS = restTemplate.getForObject(url, TeamAPI[].class);

            List<TeamAPI> teamsList = Optional.ofNullable(teamAPIS)
                    .map(Arrays::asList)
                    .orElse(null);
            teamsList.stream()
                    .filter(m -> Objects.nonNull(m.getTeamId()))
                    .forEach(m -> m.setLeagueId(leagueId));

            return teamsList;

        } catch (Exception e) {
            //TODO: Create exception
            LOGGER.error("APIFootballClient - getTeamsByLeague() | " + e.getMessage(), e);
            return null;
        }
    }

    public List<MatchAPI> getMatches(Long leaguesId, LocalDate from, LocalDate to) {
        URI url = getDefaultUrlBuilder()
                .queryParam("action", "get_events")
                .queryParam("league_id", leaguesId)
                .queryParam("from", from)
                .queryParam("to", to)
                .build()
                .encode()
                .toUri();

        try {
            LOGGER.info("APIFootballClient - getMatches() | " + "Initializing request: " + url);
            MatchAPI[] matchAPIS = restTemplate.getForObject(url, MatchAPI[].class);
            return Arrays.asList(matchAPIS);
        } catch (Exception e) {
            //TODO: Create exception
            LOGGER.error("APIFootballClient - getMatches() | " + e.getMessage(), e);
            return null;
        }
    }

    public MatchAPI getMatchById(Long matchId) {
        URI url = getDefaultUrlBuilder()
                .queryParam("action", "get_events")
//                .queryParam("league_id", leagueId)
                .queryParam("match_id", matchId)
                .build()
                .encode()
                .toUri();

        try {
            LOGGER.info("APIFootballClient - getMatchById() | " + "Initializing request: " + url);
            MatchAPI[] matchAPIList = restTemplate.getForObject(url, MatchAPI[].class);
            return Arrays.stream(matchAPIList)
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            //TODO: Create exception
            LOGGER.error("APIFootballClient - getMatchById() | " + e.getMessage(), e);
            return null;
        }
    }

    public OddsAPI getOddsByMatchId(Long matchId) {
        URI url = getDefaultUrlBuilder()
                .queryParam("action", "get_odds")
                .queryParam("match_id", matchId)
                .build()
                .encode()
                .toUri();

        try {
            LOGGER.info("APIFootballClient - getOddsByMatchId() | " + "Initializing request: " + url);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getBody().contains("\"error\"")) {
                return null;
            }

            OddsAPI[] odds = new ObjectMapper().readValue(response.getBody(), OddsAPI[].class);
            return Optional.ofNullable(odds)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList())
                    .stream()
                    .filter(o -> Objects.nonNull(o.getAwayTeamWin()) && Objects.nonNull(o.getDraw()) && Objects.nonNull(o.getHomeTeamWin()))
                    .max((o1, o2) -> {
                        BigDecimal avgO1 = (o1.getAwayTeamWin().add(o1.getDraw()).add(o1.getHomeTeamWin())).divide(new BigDecimal(3), RoundingMode.HALF_UP);
                        BigDecimal avgO2 = (o2.getAwayTeamWin().add(o2.getDraw()).add(o2.getHomeTeamWin())).divide(new BigDecimal(3), RoundingMode.HALF_UP);
                        return avgO1.compareTo(avgO2);
                    })
                    .orElse(null);
        } catch (Exception e) {
            LOGGER.error("APIFootballClient - getOddsByMatchId() | " + e.getMessage(), e);
            return null;
        }
    }
}

