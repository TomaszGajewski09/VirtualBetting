package com.projekt.VirtualBetting.model.domains;

import com.projekt.VirtualBetting.model.types.SelectedOption;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BETTING_OPTIONS")
public class BettingOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BETTING_SLIP_ID", referencedColumnName = "BETTING_SLIP_ID")
    private BettingSlip bettingSlip;

    @ManyToOne
    @JoinColumn(name = "MATCH_ID", referencedColumnName = "MATCH_ID")
    private Match match;

    @Column(name = "SELECTED_OPTION")
    @Enumerated(EnumType.STRING)
    private SelectedOption selectedOption;
}