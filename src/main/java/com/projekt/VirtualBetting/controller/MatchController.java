package com.projekt.VirtualBetting.controller;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.mapper.MatchMapper;
import com.projekt.VirtualBetting.model.domains.Match;
import com.projekt.VirtualBetting.model.dto.MatchDTO;
import com.projekt.VirtualBetting.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/matches")
public class MatchController {

    MatchMapper matchMapper;
    MatchService matchService;

    @GetMapping
    public ResponseEntity<List<MatchDTO>> getMatches() {
        return ResponseEntity.ok(matchMapper.mapToMatchDTOList(matchService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDTO> getBettingSlipById(@PathVariable Long id) throws DomainNotFoundException {
        Match match = matchService.findById(id);
        return ResponseEntity.ok(matchMapper.mapToMatchDTO(match));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        matchService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteAllMatches() {
        matchService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
