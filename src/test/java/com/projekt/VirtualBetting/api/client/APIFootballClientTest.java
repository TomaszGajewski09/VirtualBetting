package com.projekt.VirtualBetting.api.client;

import com.projekt.VirtualBetting.api.config.APIFootballConfig;
import com.projekt.VirtualBetting.api.domains.LeagueAPI;

import com.projekt.VirtualBetting.api.domains.MatchAPI;
import com.projekt.VirtualBetting.api.domains.TeamAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class APIFootballClientTest {

    @Mock
    private APIFootballConfig apiFootballConfig;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private APIFootballClient apiFootballClient;

    @BeforeEach
    void setUp() {
        when(apiFootballConfig.getApiUrl()).thenReturn("http://api.football-data.org/");
        when(apiFootballConfig.getApiKey()).thenReturn("test_api_key");
    }

    @Test
    void getLeagues_ShouldReturnListOfLeagues_WhenApiCallIsSuccessful() {
        // Given
        URI expectedUri = URI.create("http://api.football-data.org/?APIkey=test_api_key&action=get_leagues");
        LeagueAPI[] mockResponse = {new LeagueAPI()};
        when(restTemplate.getForObject(expectedUri, LeagueAPI[].class)).thenReturn(mockResponse);

        // When
        List<LeagueAPI> leagues = apiFootballClient.getLeagues();

        // Then
        assertNotNull(leagues);
        assertFalse(leagues.isEmpty());
        verify(restTemplate).getForObject(expectedUri, LeagueAPI[].class);
    }

    @Test
    void getLeagues_ShouldReturnEmptyList_WhenApiCallFails() {
        // Given
        URI expectedUri = URI.create("http://api.football-data.org/?APIkey=test_api_key&action=get_leagues");
        when(restTemplate.getForObject(expectedUri, LeagueAPI[].class)).thenThrow(RuntimeException.class);

        // When
        List<LeagueAPI> leagues = apiFootballClient.getLeagues();

        // Then
        assertNull(leagues);
        verify(restTemplate).getForObject(expectedUri, LeagueAPI[].class);
    }

    @Test
    void getLeagueById_ShouldReturnLeague_WhenApiCallIsSuccessful() {
        // Given
        Long leagueId = 1L;
        URI expectedUri = URI.create("http://api.football-data.org/?APIkey=test_api_key&action=get_leagues&league_id=" + leagueId);
        LeagueAPI mockLeague = new LeagueAPI(leagueId, "testName", 2l, "testCountry");
        LeagueAPI[] mockResponse = {mockLeague};
        when(restTemplate.getForObject(expectedUri, LeagueAPI[].class)).thenReturn(mockResponse);

        // When
        LeagueAPI league = apiFootballClient.getLeagueById(leagueId);

        // Then
        assertNotNull(league);
        verify(restTemplate).getForObject(expectedUri, LeagueAPI[].class);
    }

    @Test
    void getLeagueById_ShouldReturnNull_WhenLeagueNotFound() {
        // Given
        Long leagueId = 1L;
        URI expectedUri = URI.create("http://api.football-data.org/?APIkey=test_api_key&action=get_leagues&league_id=" + leagueId);
        LeagueAPI[] mockResponse = {};
        when(restTemplate.getForObject(expectedUri, LeagueAPI[].class)).thenReturn(mockResponse);

        // When
        LeagueAPI league = apiFootballClient.getLeagueById(leagueId);

        // Then
        assertNull(league);
        verify(restTemplate).getForObject(expectedUri, LeagueAPI[].class);
    }

    @Test
    void getTeamsByLeague_ShouldReturnListOfTeams_WhenApiCallIsSuccessful() {
        // Given
        Long leagueId = 1L;
        URI expectedUri = URI.create("http://api.football-data.org/?APIkey=test_api_key&action=get_teams&league_id=" + leagueId);
        TeamAPI[] mockResponse = {new TeamAPI()}; // Ustaw odpowiednie pola w mockTeamAPI
        when(restTemplate.getForObject(expectedUri, TeamAPI[].class)).thenReturn(mockResponse);

        // When
        List<TeamAPI> teams = apiFootballClient.getTeamsByLeague(leagueId);

        // Then
        assertNotNull(teams);
        assertFalse(teams.isEmpty());
        verify(restTemplate).getForObject(expectedUri, TeamAPI[].class);
    }

    @Test
    void getMatches_ShouldReturnListOfMatches_WhenApiCallIsSuccessful() {
        // Given
        Long leagueId = 1L;
        LocalDate from = LocalDate.now();
        LocalDate to = LocalDate.now().plusDays(7);
        URI expectedUri = URI.create("http://api.football-data.org/?APIkey=test_api_key&action=get_events&league_id=" + leagueId + "&from=" + from + "&to=" + to);
        MatchAPI[] mockResponse = {new MatchAPI()};
        when(restTemplate.getForObject(expectedUri, MatchAPI[].class)).thenReturn(mockResponse);

        // When
        List<MatchAPI> matches = apiFootballClient.getMatches(leagueId, from, to);

        // Then
        assertNotNull(matches);
        assertFalse(matches.isEmpty());
        verify(restTemplate).getForObject(expectedUri, MatchAPI[].class);
    }

    @Test
    void getMatchById_ShouldReturnMatch_WhenApiCallIsSuccessful() {
        // Given
        Long matchId = 1L;
        URI expectedUri = URI.create("http://api.football-data.org/?APIkey=test_api_key&action=get_events&match_id=" + matchId);
        MatchAPI mockMatch = new MatchAPI();
        mockMatch.setMatchId(matchId);
        MatchAPI[] mockResponse = {mockMatch};
        when(restTemplate.getForObject(expectedUri, MatchAPI[].class)).thenReturn(mockResponse);

        // When
        MatchAPI match = apiFootballClient.getMatchById(matchId);

        // Then
        assertNotNull(match);
        verify(restTemplate).getForObject(expectedUri, MatchAPI[].class);
    }

    @Test
    void getMatchById_ShouldReturnNull_WhenNoMatchIsFound() {
        // Given
        Long matchId = 1L;
        URI expectedUri = URI.create("http://api.football-data.org/?APIkey=test_api_key&action=get_events&match_id=" + matchId);
        MatchAPI[] mockResponse = {};
        when(restTemplate.getForObject(expectedUri, MatchAPI[].class)).thenReturn(mockResponse);

        // When
        MatchAPI match = apiFootballClient.getMatchById(matchId);

        // Then
        assertNull(match);
        verify(restTemplate).getForObject(expectedUri, MatchAPI[].class);
    }

    @Test
    void getTeamById_ShouldReturnTeam_WhenApiCallIsSuccessful() {
        // Given
        Long teamId = 1L;
        URI expectedUri = URI.create("http://api.football-data.org/?APIkey=test_api_key&action=get_teams&team_id=" + teamId);
        TeamAPI mockTeam = new TeamAPI(teamId, "testName", "testCountry", 1l);
        TeamAPI[] mockResponse = {mockTeam};
        when(restTemplate.getForObject(expectedUri, TeamAPI[].class)).thenReturn(mockResponse);

        // When
        TeamAPI team = apiFootballClient.getTeamById(teamId);

        // Then
        assertNotNull(team);
        verify(restTemplate).getForObject(expectedUri, TeamAPI[].class);
    }

    @Test
    void getTeamById_ShouldReturnNull_WhenNoTeamIsFound() {
        // Given
        Long teamId = 1L;
        URI expectedUri = URI.create("http://api.football-data.org/?APIkey=test_api_key&action=get_teams&team_id=" + teamId);
        TeamAPI[] mockResponse = {};
        when(restTemplate.getForObject(expectedUri, TeamAPI[].class)).thenReturn(mockResponse);

        // When
        TeamAPI team = apiFootballClient.getTeamById(teamId);

        // Then
        assertNull(team);
        verify(restTemplate).getForObject(expectedUri, TeamAPI[].class);
    }
}
