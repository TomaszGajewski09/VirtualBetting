package com.projekt.VirtualBetting.service;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.domains.Match;
import com.projekt.VirtualBetting.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;

    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    public Match findById(Long id) throws DomainNotFoundException {
        return matchRepository.findById(id).orElseThrow(() -> new DomainNotFoundException("Match"));
    }

//    public Match save(final Match match) {
//        return matchRepository.save(match);
//    }

    @Transactional
    public Match saveOrUpdate(final Match match) {
        return matchRepository.findById(match.getMatchId())
                .map(existingMatch -> updateMatch(existingMatch, match))
                .orElseGet(() -> matchRepository.save(match));
    }

    private Match updateMatch(Match existingMatch, Match newMatch) {
        existingMatch.setMatchStatus(newMatch.getMatchStatus());
        existingMatch.setHomeTeamScore(newMatch.getHomeTeamScore());
        existingMatch.setAwayTeamScore(newMatch.getAwayTeamScore());
        existingMatch.setDate(newMatch.getDate());
        existingMatch.setTime(newMatch.getTime());
        existingMatch.setLeague(newMatch.getLeague());
        existingMatch.setHomeTeam(newMatch.getHomeTeam());
        existingMatch.setAwayTeam(newMatch.getAwayTeam());

        return matchRepository.save(existingMatch);
    }

    public void deleteById(Long id) {
        matchRepository.deleteById(id);
    }

    public void deleteAll() {
        matchRepository.deleteAll();
    }
}
