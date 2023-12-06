package com.projekt.VirtualBetting.mapper;

import com.projekt.VirtualBetting.model.domains.League;
import com.projekt.VirtualBetting.model.domains.Match;
import com.projekt.VirtualBetting.model.domains.Team;
import com.projekt.VirtualBetting.model.dto.MatchDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MatchMapperTest {

    @InjectMocks
    private MatchMapper matchMapper;

    @Test
    void mapToMatchDTO_ShouldMapCorrectly() {
        // GIVEN
        Match match = new Match();
        match.setMatchId(1l);
        match.setMatchStatus("Finished");
        match.setHomeTeamScore(1);
        match.setAwayTeamScore(2);
        match.setDate(LocalDate.now());
        match.setTime(LocalTime.now());
        match.setLeague(new League());
        match.setHomeTeam(new Team());
        match.setAwayTeam(new Team());

        // WHEN
        MatchDTO result = matchMapper.mapToMatchDTO(match);

        // THEN
        assertNotNull(result);
        assertEquals(match.getMatchId(), result.getMatchId());
        assertEquals(match.getLastTimeUpdate(), result.getLastTimeUpdate());
    }

    @Test
    void mapToMatchDTOList_ShouldMapListCorrectly() {
        // GIVEN
        Match match = new Match();
        match.setMatchId(1l);
        match.setMatchStatus("Finished");
        match.setHomeTeamScore(1);
        match.setAwayTeamScore(2);
        match.setDate(LocalDate.now());
        match.setTime(LocalTime.now());
        match.setLeague(new League());
        match.setHomeTeam(new Team());
        match.setAwayTeam(new Team());

        List<Match> matches = Arrays.asList(match);

        // WHEN
        List<MatchDTO> result = matchMapper.mapToMatchDTOList(matches);

        // THEN
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}

