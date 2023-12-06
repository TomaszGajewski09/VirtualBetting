package com.projekt.VirtualBetting.mapper;

import com.projekt.VirtualBetting.model.domains.Odds;
import com.projekt.VirtualBetting.model.dto.OddsDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OddsMapperTest {

    @InjectMocks
    private OddsMapper oddsMapper;

    @Test
    void mapToOddsDTO_ShouldMapCorrectly() {
        // GIVEN
        Odds odds = new Odds();
        odds.setOddsId(1L);
        odds.setHomeWin(BigDecimal.valueOf(1.5));
        odds.setDraw(BigDecimal.valueOf(2.0));
        odds.setAwayWin(BigDecimal.valueOf(2.5));

        // WHEN
        OddsDTO result = oddsMapper.mapToOddsDTO(odds);

        // THEN
        assertNotNull(result);
        assertEquals(odds.getOddsId(), result.getOddsId());
        assertEquals(odds.getHomeWin(), result.getHomeWin());
        assertEquals(odds.getDraw(), result.getDraw());
        assertEquals(odds.getAwayWin(), result.getAwayWin());
    }

    @Test
    void mapToOddsDTOList_ShouldMapListCorrectly() {
        // GIVEN
        Odds odds = new Odds();
        odds.setOddsId(1L);
        odds.setHomeWin(BigDecimal.valueOf(1.5));
        odds.setDraw(BigDecimal.valueOf(2.0));
        odds.setAwayWin(BigDecimal.valueOf(2.5));

        List<Odds> oddsList = Arrays.asList(odds);

        // WHEN
        List<OddsDTO> result = oddsMapper.mapToOddsDTOList(oddsList);

        // THEN
        assertNotNull(result);
        assertEquals(1, result.size());
        // Assertions for individual elements as required
    }
}

