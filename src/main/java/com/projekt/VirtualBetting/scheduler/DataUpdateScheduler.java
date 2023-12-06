package com.projekt.VirtualBetting.scheduler;

import com.projekt.VirtualBetting.api.service.DataInitializerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataUpdateScheduler {
    private final DataInitializerService dataInitializerService;
    private static final Logger LOGGER = LoggerFactory.getLogger(DataInitializerService.class);

    @Scheduled(cron = "0 0 0 * * MON")
    public void weeklyFootballDataUpdate() {
        LocalDate from = LocalDate.now().minusWeeks(1);
        LocalDate to = LocalDate.now().plusMonths(1);
        dataInitializerService.initializeFootballData(from, to);
        LOGGER.info("Weekly Football Data Update completed.");
    }

    // Aktualizacja Football dwa razy dziennie
    @Scheduled(cron = "0 0 6,18 * * *")
    public void biDailyFootballDataUpdate() {
        LocalDate from = LocalDate.now();
        LocalDate to = LocalDate.now().plusWeeks(1);
        dataInitializerService.initializeFootballData(from, to);
        LOGGER.info("Bi-daily Football Data Update completed.");
    }

    // Aktualizacja Exchange Rate raz dziennie
    @Scheduled(cron = "0 0 1 * * *")
    public void dailyExchangeRateDataUpdate() {
        dataInitializerService.initializeExchangeRateData();
        LOGGER.info("Daily Exchange Rate Data Update completed.");
    }
}