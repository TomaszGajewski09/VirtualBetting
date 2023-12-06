package com.projekt.VirtualBetting.controller;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.mapper.TeamMapper;
import com.projekt.VirtualBetting.model.domains.Team;
import com.projekt.VirtualBetting.model.dto.TeamDTO;
import com.projekt.VirtualBetting.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/teams")
public class TeamController {
    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        List<Team> teamList = teamService.findAll();
        return ResponseEntity.ok(teamMapper.mapToTeamDTOList(teamList));
    }

    @GetMapping("{teamId}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Long teamId) throws DomainNotFoundException {
        return ResponseEntity.ok(teamMapper.mapToTeamDTO(teamService.findById(teamId)));
    }

    @GetMapping("/league/{leagueId}")
    public ResponseEntity<List<TeamDTO>> getTeamsByLeagueId(@PathVariable Long leagueId) throws DomainNotFoundException {
        List<Team> teamList = teamService.findByLeagueId(leagueId);
        return ResponseEntity.ok(teamMapper.mapToTeamDTOList(teamList));
    }

}
