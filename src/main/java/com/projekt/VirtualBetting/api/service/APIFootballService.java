package com.projekt.VirtualBetting.api.service;


import com.projekt.VirtualBetting.api.client.APIFootballClient;
import com.projekt.VirtualBetting.api.domains.LeagueAPI;
import com.projekt.VirtualBetting.api.domains.MatchAPI;
import com.projekt.VirtualBetting.api.domains.OddsAPI;
import com.projekt.VirtualBetting.api.domains.TeamAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class APIFootballService {

    private final APIFootballClient footballClient;

    public List<LeagueAPI> fetchLeaguesAPI() {
        return footballClient.getLeagues();
    }

    public LeagueAPI fetchLeagueAPIById(Long leagueId) {
        return footballClient.getLeagueById(leagueId);
    }

    public List<TeamAPI> fetchTeamsAPIByLeague(Long leagueId) {
        return footballClient.getTeamsByLeague(leagueId);
    }

    public OddsAPI fetchOddsByMatchId(Long matchId) {
        return footballClient.getOddsByMatchId(matchId);
    }


    public MatchAPI fetchMatchAPIById(Long matchId) {
        return footballClient.getMatchById(matchId);
    }

    public TeamAPI fetchTeamAPIById(Long teamId) {
        return footballClient.getTeamById(teamId);
    }

    public List<MatchAPI> fetchAllMatchesAPI(Long leagueId, LocalDate from, LocalDate to) {
        return footballClient.getMatches(leagueId, from, to);
    }
}
