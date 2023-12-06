package com.projekt.VirtualBetting.api.mapper;


import com.projekt.VirtualBetting.api.domains.LeagueAPI;
import com.projekt.VirtualBetting.api.domains.MatchAPI;
import com.projekt.VirtualBetting.api.domains.OddsAPI;
import com.projekt.VirtualBetting.api.domains.TeamAPI;
import com.projekt.VirtualBetting.api.service.APIFootballService;
import com.projekt.VirtualBetting.model.domains.League;
import com.projekt.VirtualBetting.model.domains.Match;
import com.projekt.VirtualBetting.model.domains.Odds;
import com.projekt.VirtualBetting.model.domains.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class APIFootballMapper {

    private final APIFootballService apiFootballService;

    public League mapToLeague(LeagueAPI leagueAPI) {
        if (leagueAPI == null) return null;
        return League.builder()
                .leagueId(leagueAPI.getLeagueId())
//                .externalLeagueId(leagueAPI.getLeagueId())
                .leagueName(leagueAPI.getLeagueName())
                .countryName(leagueAPI.getCountryName())
                .build();
    }

    public List<League> mapToLeagueList(List<LeagueAPI> list) {
        return list.stream()
                .map(this::mapToLeague)
                .collect(Collectors.toList());
    }

    public Odds mapToOdds(OddsAPI oddsAPI) {
        if (oddsAPI == null) return null;

        Match match = mapToMatch(apiFootballService.fetchMatchAPIById(oddsAPI.getMatchId()));
        return Odds.builder()
                .match(match)
//                .externalOddsId(oddsAPI.getMatchId())
                .homeWin(oddsAPI.getHomeTeamWin())
                .draw(oddsAPI.getDraw())
                .awayWin(oddsAPI.getAwayTeamWin())
                .build();
    }


    public Match mapToMatch(MatchAPI matchAPI) {
        if (matchAPI == null) return null;
        League league = apiFootballService.fetchLeaguesAPI().stream()
                .map(this::mapToLeague)
                .filter(l -> l.getLeagueId().equals(matchAPI.getLeagueId()))
                .findFirst()
                .orElse(null);
        Team homeTeam = mapToTeam(apiFootballService.fetchTeamAPIById(matchAPI.getHomeTeamId()));
        Team awayTeam = mapToTeam(apiFootballService.fetchTeamAPIById(matchAPI.getAwayTeamId()));
        return Match.builder()
                .matchId(matchAPI.getMatchId())
//                .externalMatchId(matchAPI.getMatchId())
                .matchStatus(matchAPI.getMatchStatus())
                .homeTeamScore(matchAPI.getHomeTeamScore())
                .awayTeamScore(matchAPI.getAwayTeamScore())
                .date(matchAPI.getMatchDate())
                .time(matchAPI.getMatchTime())
                .league(league)
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
//                .odds(odds)
                .build();
    }

    public List<Match> mapToMatchList(List<MatchAPI> matchAPIList) {
        return matchAPIList.stream()
                .map(this::mapToMatch)
                .collect(Collectors.toList());
    }

    public Team mapToTeam(TeamAPI teamAPI) {
        if (teamAPI == null) return null;
        League league = mapToLeague(apiFootballService.fetchLeagueAPIById(teamAPI.getLeagueId()));
        return Team.builder()
                .teamId(teamAPI.getTeamId())
//                .externalTeamId(teamAPI.getTeamId())
                .name(teamAPI.getTeamName())
                .league(league)
                .build();
    }

    public List<Team> mapToTeamList(List<TeamAPI> teamAPIList) {
        return teamAPIList.stream()
                .map(this::mapToTeam)
                .collect(Collectors.toList());
    }


}
