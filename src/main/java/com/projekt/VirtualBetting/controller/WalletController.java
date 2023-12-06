package com.projekt.VirtualBetting.controller;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.mapper.WalletMapper;
import com.projekt.VirtualBetting.model.domains.Wallet;
import com.projekt.VirtualBetting.model.dto.WalletDTO;
import com.projekt.VirtualBetting.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/wallets")
public class WalletController {

    private WalletService walletService;
    private WalletMapper walletMapper;

    @GetMapping
    public ResponseEntity<List<WalletDTO>> getAllWallets() {
        return ResponseEntity.ok(walletMapper.mapToWalletDtoList(walletService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WalletDTO> getWalletById(@PathVariable Long id) throws DomainNotFoundException {
        Wallet wallet = walletService.findById(id);
        return ResponseEntity.ok(walletMapper.mapToWalletDto(wallet));
    }

//    @PostMapping
//    public ResponseEntity<WalletDTO> createWallet(@RequestBody Wallet wallet) {
//        Wallet createdWallet = walletService.save(wallet);
//        return ResponseEntity.ok(walletMapper.mapToWalletDto(createdWallet));
//    }

    @PutMapping("/{id}")
    public ResponseEntity<WalletDTO> updateWallet(@PathVariable Long id, @RequestBody WalletDTO walletDetails) throws DomainNotFoundException {
        Wallet updatedWallet = walletService.updateWallet(id, walletDetails);
        return ResponseEntity.ok(walletMapper.mapToWalletDto(updatedWallet));
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteWallet(@PathVariable Long id) {
//        walletService.deleteById(id);
//        return ResponseEntity.ok().build();
//    }
}
