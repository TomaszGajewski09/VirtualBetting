package com.projekt.VirtualBetting.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long userid;
    private String username;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private String country;
    private String city;
    private LocalDateTime createAt;
    private Boolean isActive;
    private Long walletId;
//    private List<Long> bettingSlipIds;
}