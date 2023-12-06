package com.projekt.VirtualBetting.api.mapper;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.projekt.VirtualBetting.api.domains.LeagueAPI;
import com.projekt.VirtualBetting.api.domains.MatchAPI;
import com.projekt.VirtualBetting.api.domains.OddsAPI;
import com.projekt.VirtualBetting.api.domains.TeamAPI;
import com.projekt.VirtualBetting.api.service.APIFootballService;
import com.projekt.VirtualBetting.model.domains.League;
import com.projekt.VirtualBetting.model.domains.Match;
import com.projekt.VirtualBetting.model.domains.Odds;
import com.projekt.VirtualBetting.model.domains.Team;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class APIFootballMapperTest {

    @Mock
    private APIFootballService apiFootballService;
    @InjectMocks
    private APIFootballMapper mapper;

    @Test
    public void mapToLeague_ValidLeagueAPI_ReturnsLeague() {
        // Given
        LeagueAPI leagueAPI = new LeagueAPI();
        leagueAPI.setLeagueId(1l);
        leagueAPI.setLeagueName("testName");
        leagueAPI.setCountryId(2l);
        leagueAPI.setCountryName("testCountry");
        // When
        League result = mapper.mapToLeague(leagueAPI);

        // Then
        assertNotNull(result);
        assertEquals(1l, result.getLeagueId());
        assertEquals("testName", result.getLeagueName());
        assertEquals("testCountry", result.getCountryName());
    }

    @Test
    public void mapToMatchList_ValidMatchAPIList_ReturnsMatchList() {
        // Given
        MatchAPI matchAPI1 = new MatchAPI();
        MatchAPI matchAPI2 = new MatchAPI();
        List<MatchAPI> matchAPIList = Arrays.asList(matchAPI1, matchAPI2);

        // When
        List<Match> result = mapper.mapToMatchList(matchAPIList);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void mapToTeamList_ValidTeamAPIList_ReturnsTeamList() {
        // Given
        TeamAPI teamAPI1 = new TeamAPI();
        TeamAPI teamAPI2 = new TeamAPI();
        List<TeamAPI> teamAPIList = Arrays.asList(teamAPI1, teamAPI2);

        // When
        List<Team> result = mapper.mapToTeamList(teamAPIList);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void mapToLeagueList_ValidLeagueAPIList_ReturnsLeagueList() {
        // Given
        LeagueAPI leagueAPI1 = new LeagueAPI();
        LeagueAPI leagueAPI2 = new LeagueAPI();
        List<LeagueAPI> leagueAPIList = Arrays.asList(leagueAPI1, leagueAPI2);

        // When
        List<League> result = mapper.mapToLeagueList(leagueAPIList);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void mapToOdds_ValidOddsAPI_ReturnsOdds() {
        // Given
        APIFootballMapper mapper = new APIFootballMapper(apiFootballService);
        LocalDateTime lastUpdate = LocalDateTime.now();
        OddsAPI oddsAPI = new OddsAPI();
        oddsAPI.setMatchId(1l);
        oddsAPI.setBookmakerName("testName");
        oddsAPI.setLastUpdate(lastUpdate);
        oddsAPI.setHomeTeamWin(new BigDecimal(1.2));
        oddsAPI.setAwayTeamWin(new BigDecimal(1.3));
        oddsAPI.setDraw(new BigDecimal(1.5));

        // When
        Odds result = mapper.mapToOdds(oddsAPI);

        // Then
        assertNotNull(result);
        assertEquals(new BigDecimal(1.2) , result.getHomeWin());
        assertEquals(new BigDecimal(1.3) , result.getAwayWin());
        assertEquals(new BigDecimal(1.5) , result.getDraw());

    }
}


