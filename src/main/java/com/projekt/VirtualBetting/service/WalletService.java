package com.projekt.VirtualBetting.service;
;
import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.domains.Wallet;
import com.projekt.VirtualBetting.model.dto.WalletDTO;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import com.projekt.VirtualBetting.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class WalletService {
    private WalletRepository walletRepository;

    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }

    public Wallet findById(Long id) throws DomainNotFoundException {
        return walletRepository.findById(id).orElseThrow(() -> new DomainNotFoundException("Wallet"));
    }

    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public Wallet updateWallet(Long id, WalletDTO walletDTO) throws DomainNotFoundException {
        Wallet existingWallet = findById(id);
        Wallet updatedWallet = Wallet.builder()
                .walletId(id)
                .user(existingWallet.getUser())
                .balance((walletDTO.getBalance() != null)? walletDTO.getBalance() : existingWallet.getBalance())
                .currency((CurrencyType.isValid(walletDTO.getCurrency()))? CurrencyType.getIfValid(walletDTO.getCurrency()) : existingWallet.getCurrency())
                .lastTransactionTime(LocalDateTime.now())
                .transaction(existingWallet.getTransaction())
                .build();
        return save(updatedWallet);
    }

    public void deleteById(Long id) {
        walletRepository.deleteById(id);
    }
}