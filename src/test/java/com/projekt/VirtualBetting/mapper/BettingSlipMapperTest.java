package com.projekt.VirtualBetting.mapper;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.domains.BettingSlip;
import com.projekt.VirtualBetting.model.domains.Match;
import com.projekt.VirtualBetting.model.domains.Transaction;
import com.projekt.VirtualBetting.model.domains.User;
import com.projekt.VirtualBetting.model.dto.BettingOptionDTO;
import com.projekt.VirtualBetting.model.dto.BettingSlipDTO;
import com.projekt.VirtualBetting.model.types.BettingSlipStatus;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import com.projekt.VirtualBetting.service.MatchService;
import com.projekt.VirtualBetting.service.TransactionService;
import com.projekt.VirtualBetting.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BettingSlipMapperTest {

    @Mock
    private UserService userService;
    @Mock
    private TransactionService transactionService;
    @Mock
    private MatchService matchService;
    @Mock
    private BettingOptionMapper bettingOptionMapper;

    @InjectMocks
    private BettingSlipMapper bettingSlipMapper;

    @Test
    void mapToBettingSlip_ShouldMapCorrectly() throws DomainNotFoundException {
        // GIVEN
        BettingSlipDTO bettingSlipDTO = new BettingSlipDTO();
        bettingSlipDTO.setBettingSlipId(1l);
        bettingSlipDTO.setUserId(1l);
        bettingSlipDTO.setStatus("PAID");
        bettingSlipDTO.setCurrency("PLN");
        bettingSlipDTO.setWageredAmount(new BigDecimal(10.0));
        bettingSlipDTO.setPossiblePayoutAmount(new BigDecimal(50.0));
        bettingSlipDTO.setTransactionsIds(new ArrayList<>(Arrays.asList(1l)));
        bettingSlipDTO.setBettingOptions(new ArrayList<>(Arrays.asList(new BettingOptionDTO(1l, "HOME"))));



        when(userService.findUserById(any())).thenReturn(new User());
        when(transactionService.findById(any())).thenReturn(new Transaction());
        when(matchService.findById(any())).thenReturn(new Match());

        // WHEN
        BettingSlip result = bettingSlipMapper.mapToBettingSlip(bettingSlipDTO);

        // THEN
        assertNotNull(result);
        assertEquals(bettingSlipDTO.getBettingSlipId(), result.getBettingSlipId());
    }

    @Test
    void mapToBettingSlipDTO_ShouldMapCorrectly() {
        // GIVEN
        BettingSlip bettingSlip = new BettingSlip();
        bettingSlip.setBettingSlipId(1l);
        bettingSlip.setUser(new User());
        bettingSlip.setStatus(BettingSlipStatus.WIN);
        bettingSlip.setCurrency(CurrencyType.PLN);
        bettingSlip.setWageredAmount(new BigDecimal(10.0));
        bettingSlip.setPossiblePayoutAmount(new BigDecimal(50.0));

        when(bettingOptionMapper.mapToBettingOptionDTOList(any())).thenReturn(Collections.emptyList());

        // WHEN
        BettingSlipDTO result = bettingSlipMapper.mapToBettingSlipDTO(bettingSlip);

        // THEN
        assertNotNull(result);
        assertEquals(bettingSlip.getBettingSlipId(), result.getBettingSlipId());
    }

    @Test
    void mapToBettingSlipDTOList_ShouldMapListCorrectly() {
        // GIVEN
        BettingSlip bettingSlip = new BettingSlip();
        bettingSlip.setBettingSlipId(1l);
        bettingSlip.setUser(new User());
        bettingSlip.setStatus(BettingSlipStatus.WIN);
        bettingSlip.setCurrency(CurrencyType.PLN);
        bettingSlip.setWageredAmount(new BigDecimal(10.0));
        bettingSlip.setPossiblePayoutAmount(new BigDecimal(50.0));
        List<BettingSlip> bettingSlips = Arrays.asList(bettingSlip);

        when(bettingOptionMapper.mapToBettingOptionDTOList(any())).thenReturn(Collections.emptyList());

        // WHEN
        List<BettingSlipDTO> result = bettingSlipMapper.mapToBettingSlipDTOList(bettingSlips);

        // THEN
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
