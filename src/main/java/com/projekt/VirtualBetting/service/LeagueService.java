package com.projekt.VirtualBetting.service;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.domains.League;
import com.projekt.VirtualBetting.repository.LeagueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeagueService {
    private final LeagueRepository leagueRepository;

    public List<League> findAll() {
        return leagueRepository.findAll();
    }

    public League findById(Long id) throws DomainNotFoundException {
        return leagueRepository.findById(id).orElseThrow(() -> new DomainNotFoundException("League"));
    }

//    public League save(final League league) {
//        return leagueRepository.save(league);
//    }

    @Transactional
    public League saveOrUpdate(final League league) {
        return leagueRepository.findById(league.getLeagueId())
                .map(existingLeague -> updateLeague(existingLeague, league))
                .orElseGet(() -> leagueRepository.save(league));
    }

    private League updateLeague(League existingLeague, League newLeague) {
        existingLeague.setLeagueName(newLeague.getLeagueName());
        existingLeague.setCountryName(newLeague.getCountryName());
        existingLeague.setMatches(newLeague.getMatches());
        existingLeague.setTeams(newLeague.getTeams());
        return leagueRepository.save(existingLeague);
    }

    public void delete(Long id) {
        leagueRepository.deleteById(id);
    }

    public void deleteAll() {
        leagueRepository.deleteAll();
    }
}
