package com.projekt.VirtualBetting.mapper;

import com.projekt.VirtualBetting.model.domains.Odds;
import com.projekt.VirtualBetting.model.dto.OddsDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OddsMapper {

    public OddsDTO mapToOddsDTO(Odds odds) {
        return OddsDTO.builder()
                .oddsId(odds.getOddsId())
                .matchId((odds.getMatch() != null)? odds.getMatch().getMatchId() : null)
                .homeWin(odds.getHomeWin())
                .draw(odds.getDraw())
                .awayWin(odds.getAwayWin())
                .build();
    }

    public List<OddsDTO> mapToOddsDTOList(List<Odds> oddsList) {
        return oddsList.stream()
                .map(this::mapToOddsDTO)
                .collect(Collectors.toList());
    }
}
