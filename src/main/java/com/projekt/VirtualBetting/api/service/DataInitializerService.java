package com.projekt.VirtualBetting.api.service;

import com.projekt.VirtualBetting.api.facade.APIExchangeRateFacade;
import com.projekt.VirtualBetting.api.facade.APIFootballFacade;
import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.domains.*;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import com.projekt.VirtualBetting.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataInitializerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataInitializerService.class);

    private final APIFootballFacade apiFootballFacade;
    private final MatchService matchService;
    private final OddsService oddsService;
    private final TeamService teamService;
    private final LeagueService leagueService;

    private final APIExchangeRateFacade apiExchangeRateFacade;
    private final ExchangeRateService exchangeRateService;

    @Transactional
    public void initializeFootballData(LocalDate from, LocalDate to) {
        List<League> leagues = apiFootballFacade.fetchLeagues();
        leagues.forEach(leagueService::saveOrUpdate);

        leagues.forEach(league -> {
            List<Team> teams = apiFootballFacade.fetchTeams(league.getLeagueId());
            teams.forEach(teamService::saveOrUpdate);
        });

        leagues.forEach(league -> {
            List<Match> matches = apiFootballFacade.fetchAllMatches(league.getLeagueId(), from, to);
            matches.forEach(match -> {
                match = matchService.saveOrUpdate(match);

                // Pobierz i zapisz kursy dla każdego meczu
                Odds odds = apiFootballFacade.fetchOddsByMatchId(match.getMatchId());
                if (odds != null) {
                    odds.setMatch(match);
                    try {
                        oddsService.saveOrUpdate(odds);
                    } catch (DomainNotFoundException e) {
                        LOGGER.error("Error saving odds for match " + match.getMatchId(), e);
                    }
                } else {
                    LOGGER.warn("No odds found for match " + match.getMatchId());
                }
            });
        });
    }

    public void initializeExchangeRateData() {
        for (CurrencyType currencyType : CurrencyType.values()) {
            System.out.println(currencyType.name());
            try {
                ExchangeRate exchangeRate = apiExchangeRateFacade.fetchExchangeRate(currencyType.name());
                if (exchangeRate != null) {
                    exchangeRateService.save(exchangeRate);
                } else {
                    LOGGER.error("No exchange rate found for " + currencyType.name());
                }
            } catch (Exception e) {
                LOGGER.error("Exception while fetching exchange rate for " + currencyType.name(), e);
            }
        }
    }
}

