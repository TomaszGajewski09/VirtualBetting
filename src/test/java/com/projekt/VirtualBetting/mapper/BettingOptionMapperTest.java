package com.projekt.VirtualBetting.mapper;

import com.projekt.VirtualBetting.model.domains.BettingOption;
import com.projekt.VirtualBetting.model.domains.Match;
import com.projekt.VirtualBetting.model.dto.BettingOptionDTO;
import com.projekt.VirtualBetting.model.types.SelectedOption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BettingOptionMapperTest {

    @InjectMocks
    private BettingOptionMapper bettingOptionMapper;

    @Test
    void mapToBettingOptionDTO_ShouldMapCorrectly() {
        // GIVEN
        BettingOption bettingOption = new BettingOption();
        bettingOption.setMatch(new Match());
        bettingOption.setSelectedOption(SelectedOption.HOME);

        // WHEN
        BettingOptionDTO result = bettingOptionMapper.mapToBettingOptionDTO(bettingOption);

        // THEN
        assertNotNull(result);
        assertEquals(bettingOption.getMatch().getMatchId(), result.getMatchId());
        assertEquals(bettingOption.getSelectedOption().toString(), result.getSelectedOption());
    }

    @Test
    void mapToBettingOptionDTOList_ShouldMapListCorrectly() {
        // GIVEN
        BettingOption bettingOption1 = new BettingOption();
        bettingOption1.setMatch(new Match());
        bettingOption1.setSelectedOption(SelectedOption.HOME);

        BettingOption bettingOption2 = new BettingOption();
        bettingOption2.setMatch(new Match());
        bettingOption2.setSelectedOption(SelectedOption.AWAY);

        List<BettingOption> bettingOptions = Arrays.asList(bettingOption1, bettingOption2);

        // WHEN
        List<BettingOptionDTO> result = bettingOptionMapper.mapToBettingOptionDTOList(bettingOptions);

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(bettingOption1.getMatch().getMatchId(), result.get(0).getMatchId());
        assertEquals(bettingOption1.getSelectedOption().toString(), result.get(0).getSelectedOption());
        assertEquals(bettingOption2.getMatch().getMatchId(), result.get(1).getMatchId());
        assertEquals(bettingOption2.getSelectedOption().toString(), result.get(1).getSelectedOption());
    }
}

