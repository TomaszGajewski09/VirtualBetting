package com.projekt.VirtualBetting.controller;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.mapper.OddsMapper;
import com.projekt.VirtualBetting.model.domains.Odds;
import com.projekt.VirtualBetting.model.dto.OddsDTO;
import com.projekt.VirtualBetting.service.OddsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/odds")
public class OddsController {

    private final OddsService oddsService;
    private final OddsMapper oddsMapper;

    @GetMapping
    public ResponseEntity<List<OddsDTO>> getAllOdds() {
        List<Odds> oddsList = oddsService.findAllOdds();
        return ResponseEntity.ok(oddsMapper.mapToOddsDTOList(oddsList));
    }

    @GetMapping("/{oddsId}")
    public ResponseEntity<OddsDTO> getOddsById(@PathVariable Long oddsId) throws DomainNotFoundException {
        return ResponseEntity.ok(oddsMapper.mapToOddsDTO(oddsService.findById(oddsId)));
    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity<OddsDTO> getOddsByMatchId(@PathVariable Long matchId) throws DomainNotFoundException {
        return ResponseEntity.ok(oddsMapper.mapToOddsDTO(oddsService.findByMatchId(matchId)));
    }
}
