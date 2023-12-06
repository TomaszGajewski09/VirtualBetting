package com.projekt.VirtualBetting.mapper;

import com.projekt.VirtualBetting.model.domains.Team;
import com.projekt.VirtualBetting.model.dto.TeamDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TeamMapperTest {

    @InjectMocks
    private TeamMapper teamMapper;

    @Test
    void mapToTeamDTO_ShouldMapCorrectly() {
        // GIVEN
        Team team = new Team();
        team.setTeamId(1L);
        team.setName("Team One");

        // WHEN
        TeamDTO result = teamMapper.mapToTeamDTO(team);

        // THEN
        assertNotNull(result);
        assertEquals(team.getTeamId(), result.getTeamId());
        assertEquals(team.getName(), result.getName());
    }

    @Test
    void mapToTeamDTOList_ShouldMapListCorrectly() {
        // GIVEN
        Team team1 = new Team();
        team1.setTeamId(1L);
        team1.setName("Team One");

        Team team2 = new Team();
        team2.setTeamId(1L);
        team2.setName("Team One");

        List<Team> teamList = Arrays.asList(team1, team2);

        // WHEN
        List<TeamDTO> result = teamMapper.mapToTeamDTOList(teamList);

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}

