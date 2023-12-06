package com.projekt.VirtualBetting.service;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.domains.Match;
import com.projekt.VirtualBetting.model.domains.Odds;
import com.projekt.VirtualBetting.repository.OddsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OddsService {
    private final OddsRepository oddsRepository;
    private final MatchService matchService;

    public List<Odds> findAllOdds() {
        return oddsRepository.findAll();
    }

    public Odds findById(Long id) throws DomainNotFoundException {
        return oddsRepository.findById(id).orElseThrow(() -> new DomainNotFoundException("Odds"));
    }


    @Transactional
    public Odds saveOrUpdate(final Odds odds) throws DomainNotFoundException {
        Match match = matchService.findById(odds.getMatch().getMatchId());
        return oddsRepository.findByMatch(match)
                .map(existingOdds -> updateOdds(existingOdds, odds))
                .orElseGet(() -> oddsRepository.save(odds));
    }

    private Odds updateOdds(Odds existingOdds, Odds newOdds) {
        existingOdds.setHomeWin(newOdds.getHomeWin());
        existingOdds.setDraw(newOdds.getDraw());
        existingOdds.setAwayWin(newOdds.getAwayWin());
        return oddsRepository.save(existingOdds);
    }


    public void deleteById(Long id) {
        oddsRepository.deleteById(id);
    }

    public void deleteAll() {
        oddsRepository.deleteAll();
    }

    public Odds findByMatchId(Long matchId) throws DomainNotFoundException {
        Match match = matchService.findById(matchId);
        return oddsRepository.findByMatch(match).orElseThrow(() -> new DomainNotFoundException("Odds"));
    }
}
