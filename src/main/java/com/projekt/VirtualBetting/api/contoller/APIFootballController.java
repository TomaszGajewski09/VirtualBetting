package com.projekt.VirtualBetting.api.contoller;


import com.projekt.VirtualBetting.api.facade.APIFootballFacade;
import com.projekt.VirtualBetting.mapper.MatchMapper;
import com.projekt.VirtualBetting.model.domains.League;
import com.projekt.VirtualBetting.model.domains.Match;
import com.projekt.VirtualBetting.model.domains.Odds;
import com.projekt.VirtualBetting.model.domains.Team;
import com.projekt.VirtualBetting.model.dto.MatchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/football")
@RequiredArgsConstructor
@CrossOrigin("*")
public class APIFootballController {

    private final APIFootballFacade footballFacade;
    private final MatchMapper matchMapper;


    @GetMapping(value = "/matches/{matchId}")
    public Match getMatchesById(@PathVariable Long matchId) {
        return footballFacade.fetchMatchById(matchId);
    }

    @GetMapping(value = "/matches/{leagueId}/{from}/{to}")
    public List<MatchDTO> getMatches(@PathVariable Long leagueId, @PathVariable LocalDate from, @PathVariable LocalDate to) {
        return matchMapper.mapToMatchDTOList(footballFacade.fetchAllMatches(leagueId, from, to));
    }

    @GetMapping(value = "/odds/{matchId}")
    public Odds getOddsByMatchId(@PathVariable Long matchId) {
        return footballFacade.fetchOddsByMatchId(matchId);
    }

    @GetMapping("/teams/league/{leagueId}")
    public List<Team> getTeams(@PathVariable Long leagueId) {
        return footballFacade.fetchTeams(leagueId);
    }

    @GetMapping("/teams/id/{teamId}")
    private Team getTeamById(@PathVariable Long teamId) {
        return footballFacade.fetchTeamById(teamId);
    }


    @GetMapping("/leagues")
    public List<League> getLeagues() {
        return footballFacade.fetchLeagues();
    }

    @GetMapping("/leagues/{leagueId}")
    public League getLeaguesById(@PathVariable Long leagueId) {
        return footballFacade.fetchLeagueById(leagueId);
    }
}
