package com.projekt.VirtualBetting.mapper;

import com.projekt.VirtualBetting.model.domains.Team;
import com.projekt.VirtualBetting.model.dto.TeamDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamMapper {

    public TeamDTO mapToTeamDTO(Team team) {
        return TeamDTO.builder()
                .teamId(team.getTeamId())
                .name(team.getName())
                .leagueId((team.getLeague() != null)? team.getLeague().getLeagueId() : null)
                .build();
    }

    public List<TeamDTO> mapToTeamDTOList(List<Team> teamList) {
        return teamList.stream()
                .map(this::mapToTeamDTO)
                .collect(Collectors.toList());
    }
}
