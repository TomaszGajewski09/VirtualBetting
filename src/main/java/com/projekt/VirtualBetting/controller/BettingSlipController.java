package com.projekt.VirtualBetting.controller;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.exception.InsufficientBalanceException;
import com.projekt.VirtualBetting.exception.MissingFieldException;
import com.projekt.VirtualBetting.exception.ValidationException;
import com.projekt.VirtualBetting.mapper.BettingSlipMapper;
import com.projekt.VirtualBetting.model.domains.BettingSlip;
import com.projekt.VirtualBetting.model.dto.BettingSlipDTO;
import com.projekt.VirtualBetting.service.BettingCalculationService;
import com.projekt.VirtualBetting.service.BettingSlipService;
import com.projekt.VirtualBetting.validator.BettingSlipValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/bettingsilps")
public class BettingSlipController {

    private final BettingSlipService bettingSlipService;
    private final BettingSlipMapper bettingSlipMapper;
    private final BettingSlipValidator bettingSlipValidator;
    private final BettingCalculationService bettingCalculationService;
    @GetMapping
    public ResponseEntity<List<BettingSlipDTO>> getAllBettingSlips() {
        return ResponseEntity.ok(bettingSlipMapper.mapToBettingSlipDTOList(bettingSlipService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BettingSlipDTO> getBettingSlipById(@PathVariable Long id) throws DomainNotFoundException {
        BettingSlip bettingSlip = bettingSlipService.findById(id);
        return ResponseEntity.ok(bettingSlipMapper.mapToBettingSlipDTO(bettingSlip));
    }

    @PostMapping
    public ResponseEntity<BettingSlipDTO> createBettingSlip(@RequestBody BettingSlipDTO bettingSlipDTO) throws DomainNotFoundException, ValidationException, MissingFieldException, InsufficientBalanceException {
        bettingSlipValidator.validateBettingSlip(bettingSlipDTO);

        bettingSlipDTO.setPossiblePayoutAmount(bettingCalculationService.calculatePotentialWin(bettingSlipDTO));
        BettingSlip bettingSlip = bettingSlipMapper.mapToBettingSlip(bettingSlipDTO);
        bettingSlip = bettingSlipService.save(bettingSlip);
        return ResponseEntity.ok(bettingSlipMapper.mapToBettingSlipDTO(bettingSlip));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBettingSlip(@PathVariable Long id) {
        bettingSlipService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping()
    public ResponseEntity<Void> deleteAllBettingSlip() {
        bettingSlipService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
