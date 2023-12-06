package com.projekt.VirtualBetting.api.facade;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.projekt.VirtualBetting.api.domains.*;
import com.projekt.VirtualBetting.api.mapper.APIFootballMapper;
import com.projekt.VirtualBetting.api.service.APIFootballService;
import com.projekt.VirtualBetting.model.domains.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class APIFootballFacadeTest {

    @Mock
    private APIFootballService apiFootballService;
    @Mock
    private APIFootballMapper apiFootballMapper;

    @InjectMocks
    private APIFootballFacade apiFootballFacade;

    @Test
    public void fetchLeagues_ReturnsListOfLeagues() {
        // Given
        List<LeagueAPI> leagueAPIList = Arrays.asList(new LeagueAPI());
        List<League> leagueList = Arrays.asList(new League());
        when(apiFootballService.fetchLeaguesAPI()).thenReturn(leagueAPIList);
        when(apiFootballMapper.mapToLeagueList(leagueAPIList)).thenReturn(leagueList);

        // When
        List<League> result = apiFootballFacade.fetchLeagues();

        // Then
        assertNotNull(result);
        assertEquals(leagueList, result);
        verify(apiFootballService).fetchLeaguesAPI();
        verify(apiFootballMapper).mapToLeagueList(leagueAPIList);
    }

    @Test
    public void fetchLeagueById_ReturnsLeague() {
        // Given
        Long leagueId = 1L;
        LeagueAPI leagueAPI = new LeagueAPI();
        League league = new League();
        when(apiFootballService.fetchLeagueAPIById(leagueId)).thenReturn(leagueAPI);
        when(apiFootballMapper.mapToLeague(leagueAPI)).thenReturn(league);

        // When
        League result = apiFootballFacade.fetchLeagueById(leagueId);

        // Then
        assertNotNull(result);
        assertEquals(league, result);
    }

    @Test
    public void fetchOddsByMatchId_ReturnsOdds() {
        // Given
        Long matchId = 1L;
        OddsAPI oddsAPI = new OddsAPI();
        Odds odds = new Odds();
        when(apiFootballService.fetchOddsByMatchId(matchId)).thenReturn(oddsAPI);
        when(apiFootballMapper.mapToOdds(oddsAPI)).thenReturn(odds);

        // When
        Odds result = apiFootballFacade.fetchOddsByMatchId(matchId);

        // Then
        assertNotNull(result);
        assertEquals(odds, result);
    }

    @Test
    public void fetchMatchById_ReturnsMatch() {
        // Given
        Long matchId = 1L;
        MatchAPI matchAPI = new MatchAPI();
        Match match = new Match();
        when(apiFootballService.fetchMatchAPIById(matchId)).thenReturn(matchAPI);
        when(apiFootballMapper.mapToMatch(matchAPI)).thenReturn(match);

        // When
        Match result = apiFootballFacade.fetchMatchById(matchId);

        // Then
        assertNotNull(result);
        assertEquals(match, result);
    }

    @Test
    public void fetchAllMatches_ReturnsListOfMatches() {
        // Given
        Long leagueId = 1L;
        LocalDate from = LocalDate.now();
        LocalDate to = LocalDate.now().plusDays(7);
        List<MatchAPI> matchAPIList = Arrays.asList(new MatchAPI());
        List<Match> matchList = Arrays.asList(new Match());
        when(apiFootballService.fetchAllMatchesAPI(leagueId, from, to)).thenReturn(matchAPIList);
        when(apiFootballMapper.mapToMatchList(matchAPIList)).thenReturn(matchList);

        // When
        List<Match> result = apiFootballFacade.fetchAllMatches(leagueId, from, to);

        // Then
        assertNotNull(result);
        assertEquals(matchList, result);
    }

    @Test
    public void fetchTeamById_ReturnsTeam() {
        // Given
        Long teamId = 1L;
        TeamAPI teamAPI = new TeamAPI();
        Team team = new Team();
        when(apiFootballService.fetchTeamAPIById(teamId)).thenReturn(teamAPI);
        when(apiFootballMapper.mapToTeam(teamAPI)).thenReturn(team);

        // When
        Team result = apiFootballFacade.fetchTeamById(teamId);

        // Then
        assertNotNull(result);
        assertEquals(team, result);
    }

    @Test
    public void fetchTeams_ReturnsListOfTeams() {
        // Given
        Long leagueId = 1L;
        List<TeamAPI> teamAPIList = Arrays.asList(new TeamAPI());
        List<Team> teamList = Arrays.asList(new Team());
        when(apiFootballService.fetchTeamsAPIByLeague(leagueId)).thenReturn(teamAPIList);
        when(apiFootballMapper.mapToTeamList(teamAPIList)).thenReturn(teamList);

        // When
        List<Team> result = apiFootballFacade.fetchTeams(leagueId);

        // Then
        assertNotNull(result);
        assertEquals(teamList, result);
    }
}

