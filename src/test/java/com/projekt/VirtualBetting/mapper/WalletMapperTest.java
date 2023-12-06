package com.projekt.VirtualBetting.mapper;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.domains.User;
import com.projekt.VirtualBetting.model.domains.Wallet;
import com.projekt.VirtualBetting.model.dto.WalletDTO;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import com.projekt.VirtualBetting.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class WalletMapperTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private WalletMapper walletMapper;

    @Test
    void mapToWallet_ShouldMapCorrectly() throws DomainNotFoundException {
        // GIVEN
        WalletDTO walletDto = new WalletDTO();
        walletDto.setWalletId(1l);
        walletDto.setUserId(1l);
        walletDto.setBalance(new BigDecimal(10.0));
        walletDto.setCurrency("PLN");

        when(userService.findUserById(anyLong())).thenReturn(new User());

        // WHEN
        Wallet result = walletMapper.mapToWallet(walletDto);

        // THEN
        assertNotNull(result);
        assertEquals(walletDto.getBalance(), result.getBalance());
    }

    @Test
    void mapToWalletDto_ShouldMapCorrectly() {
        // GIVEN
        Wallet wallet = new Wallet();
        wallet.setWalletId(1l);
        wallet.setUser(new User());
        wallet.setBalance(new BigDecimal(10.0));
        wallet.setCurrency(CurrencyType.PLN);

        // WHEN
        WalletDTO result = walletMapper.mapToWalletDto(wallet);

        // THEN
        assertNotNull(result);
        assertEquals(wallet.getBalance(), result.getBalance());
    }

    @Test
    void mapToWalletDtoList_ShouldMapListCorrectly() {
        // GIVEN
        Wallet wallet1 = new Wallet();
        wallet1.setWalletId(1l);
        wallet1.setUser(new User());
        wallet1.setBalance(new BigDecimal(10.0));
        wallet1.setCurrency(CurrencyType.PLN);

        Wallet wallet2 = new Wallet();
        wallet2.setWalletId(2l);
        wallet2.setUser(new User());
        wallet2.setBalance(new BigDecimal(20.0));
        wallet2.setCurrency(CurrencyType.USD);

        List<Wallet> wallets = Arrays.asList(wallet1, wallet2);

        // WHEN
        List<WalletDTO> result = walletMapper.mapToWalletDtoList(wallets);

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        // Additional assertions as needed
    }
}

