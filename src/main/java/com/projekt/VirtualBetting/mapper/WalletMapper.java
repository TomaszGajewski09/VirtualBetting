package com.projekt.VirtualBetting.mapper;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.domains.Wallet;
import com.projekt.VirtualBetting.model.dto.WalletDTO;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import com.projekt.VirtualBetting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletMapper {

    private final UserService userService;

    // todo: do poprawy (jeśli będzie używane)
    public Wallet mapToWallet(WalletDTO walletDto) throws DomainNotFoundException {
        return Wallet.builder()
                .walletId(walletDto.getWalletId())
                .user((walletDto.getUserId() != null)? userService.findUserById(walletDto.getUserId()) : null)
                .balance(walletDto.getBalance())
                .currency(CurrencyType.getIfValid(walletDto.getCurrency()))
                .lastTransactionTime(walletDto.getLastTransactionTime())
//                .transaction()
                .build();
    }
    public WalletDTO mapToWalletDto(Wallet wallet) {
        return WalletDTO.builder()
                .walletId(wallet.getWalletId())
                .userId((wallet.getUser() != null)? wallet.getUser().getUserid() : null)
                .balance(wallet.getBalance())
                .currency(wallet.getCurrency().toString())
                .lastTransactionTime(wallet.getLastTransactionTime())
                .transactionIds(
                        wallet.getTransaction().stream()
                                .map(t -> t.getTransactionId())
                                .collect(Collectors.toList())
                )
                .build();
    }

    public List<WalletDTO> mapToWalletDtoList(List<Wallet> wallets) {
        return wallets.stream()
                .map(this::mapToWalletDto)
                .collect(Collectors.toList());
    }
}
