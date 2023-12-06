package com.projekt.VirtualBetting.api.contoller;


import com.projekt.VirtualBetting.api.service.DataInitializerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class DataInitializationController {

    private final DataInitializerService dataInitializerService;


    @PostMapping("/initialize-data/football")
    public ResponseEntity<String> initializeFootballData() {
        LocalDate from = LocalDate.now().minusWeeks(2);
        LocalDate to = LocalDate.now().plusMonths(2);
        dataInitializerService.initializeFootballData(from, to);
        return ResponseEntity.ok("Dane zostały zainicjowane.");
    }

    @PostMapping("initialize-data/exchange-rate")
    public ResponseEntity<String> initializeExchangeRateData() {
        dataInitializerService.initializeExchangeRateData();
        return ResponseEntity.ok("Dane zostały zainicjowane");
    }
}
