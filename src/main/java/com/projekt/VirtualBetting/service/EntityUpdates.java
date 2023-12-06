package com.projekt.VirtualBetting.service;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.mapper.WalletMapper;
import com.projekt.VirtualBetting.model.domains.Transaction;
import com.projekt.VirtualBetting.model.domains.Wallet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EntityUpdates {
    private WalletService walletService;
    private WalletMapper walletMapper;

    public void walletUpdate(Transaction transaction) throws DomainNotFoundException {
        Long walletId = transaction.getWallet().getWalletId();
        Wallet walletToUpdate = walletService.findById(walletId);

        walletToUpdate.setBalance(walletToUpdate.getBalance().add(transaction.getAmount()));
        walletService.updateWallet(walletId, walletMapper.mapToWalletDto(walletToUpdate));
    }
}
