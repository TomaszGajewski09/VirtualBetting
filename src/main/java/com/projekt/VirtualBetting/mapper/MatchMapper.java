package com.projekt.VirtualBetting.mapper;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.domains.Match;
import com.projekt.VirtualBetting.model.dto.MatchDTO;
import com.projekt.VirtualBetting.service.BettingSlipService;
import com.projekt.VirtualBetting.service.LeagueService;
import com.projekt.VirtualBetting.service.OddsService;
import com.projekt.VirtualBetting.service.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MatchMapper {
    private final LeagueService leagueService;
    private final TeamService teamService;
    private final OddsService oddsService;
    private final BettingSlipService bettingSlipService;

    public MatchDTO mapToMatchDTO(Match match) {
        return MatchDTO.builder()
                .matchId(match.getMatchId())
//                .externalMatchId(match.getExternalMatchId())
                .lastTimeUpdate(match.getLastTimeUpdate())
                .leagueId((match.getLeague() != null) ? match.getLeague().getLeagueId() : null)
                .matchStatus(match.getMatchStatus().toString())
                .homeTeamId((match.getHomeTeam() != null) ? match.getHomeTeam().getTeamId() : null)
                .awayTeamId((match.getAwayTeam() != null) ? match.getAwayTeam().getTeamId() : null)
                .homeTeamScore(match.getHomeTeamScore())
                .awayTeamScore(match.getAwayTeamScore())
                .date(match.getDate())
                .time(match.getTime())
                .oddsId((match.getOdds() != null) ? match.getOdds().getOddsId() : null)
//                .bettingSlipIds(bettingSlipIds)
                .build();
    }

    public List<MatchDTO> mapToMatchDTOList(List<Match> matches) {
        return matches.stream()
                .map(this::mapToMatchDTO)
                .collect(Collectors.toList());
    }
}
