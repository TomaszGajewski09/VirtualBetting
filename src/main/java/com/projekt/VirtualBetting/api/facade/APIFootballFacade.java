package com.projekt.VirtualBetting.api.facade;


import com.projekt.VirtualBetting.api.domains.LeagueAPI;
import com.projekt.VirtualBetting.api.domains.MatchAPI;
import com.projekt.VirtualBetting.api.domains.OddsAPI;
import com.projekt.VirtualBetting.api.domains.TeamAPI;
import com.projekt.VirtualBetting.api.mapper.APIFootballMapper;
import com.projekt.VirtualBetting.api.service.APIFootballService;
import com.projekt.VirtualBetting.model.domains.League;
import com.projekt.VirtualBetting.model.domains.Match;
import com.projekt.VirtualBetting.model.domains.Odds;
import com.projekt.VirtualBetting.model.domains.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class APIFootballFacade {

    private final APIFootballService apiFootballService;
    private final APIFootballMapper apiFootballMapper;

    public List<League> fetchLeagues() {
        List<LeagueAPI> leagueAPIList = apiFootballService.fetchLeaguesAPI();
        return apiFootballMapper.mapToLeagueList(leagueAPIList);
    }

    public League fetchLeagueById(Long leagueId) {
        LeagueAPI leagueAPI = apiFootballService.fetchLeagueAPIById(leagueId);
        return apiFootballMapper.mapToLeague(leagueAPI);
    }

    public Odds fetchOddsByMatchId(Long matchId) {
        OddsAPI oddsAPI = apiFootballService.fetchOddsByMatchId(matchId);
        return apiFootballMapper.mapToOdds(oddsAPI);
    }

    public Match fetchMatchById(Long matchId) {
        MatchAPI matchAPI = apiFootballService.fetchMatchAPIById(matchId);
        return apiFootballMapper.mapToMatch(matchAPI);
    }

    public List<Match> fetchAllMatches(Long leagueId, LocalDate from, LocalDate to) {
        List<MatchAPI> matchAPIList = apiFootballService.fetchAllMatchesAPI(leagueId, from, to);
        return apiFootballMapper.mapToMatchList(matchAPIList);
    }

    public Team fetchTeamById(Long teamId) {
        TeamAPI teamAPI = apiFootballService.fetchTeamAPIById(teamId);
        return apiFootballMapper.mapToTeam(teamAPI);
    }

    public List<Team> fetchTeams(Long leagueId) {
        List<TeamAPI> teamAPIList = apiFootballService.fetchTeamsAPIByLeague(leagueId);
        return apiFootballMapper.mapToTeamList(teamAPIList);
    }
}
