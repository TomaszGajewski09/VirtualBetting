package com.projekt.VirtualBetting.mapper;

import com.projekt.VirtualBetting.model.domains.BettingOption;
import com.projekt.VirtualBetting.model.dto.BettingOptionDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BettingOptionMapper {

    public BettingOptionDTO mapToBettingOptionDTO(BettingOption bettingOption) {
        return BettingOptionDTO.builder()
                .matchId(bettingOption.getMatch().getMatchId())
                .selectedOption(bettingOption.getSelectedOption().toString())
                .build();
    }

    public List<BettingOptionDTO> mapToBettingOptionDTOList(List<BettingOption> bettingOptionList) {
        return bettingOptionList.stream()
                .map(this::mapToBettingOptionDTO)
                .collect(Collectors.toList());
    }
}
