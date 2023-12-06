package com.projekt.VirtualBetting.mapper;

import com.projekt.VirtualBetting.model.domains.User;
import com.projekt.VirtualBetting.model.dto.UserDTO;
import com.projekt.VirtualBetting.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserMapper {

    public User mapToUser(final UserDTO userDto) {
        return User.builder()
                .userid(userDto.getUserid())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .dateOfBirth(userDto.getDateOfBirth())
                .country(userDto.getCountry())
                .city(userDto.getCity())
                .createAt(userDto.getCreateAt())
                .isActive(userDto.getIsActive())
//                .wallet(walletService.findById(userDto.getWalletId()))
                .build();
    }

    public UserDTO mapToUserDto(final User user) {
        return UserDTO.builder()
                .userid(user.getUserid())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .dateOfBirth(user.getDateOfBirth())
                .country(user.getCountry())
                .city(user.getCity())
                .createAt(user.getCreateAt())
                .isActive(user.getIsActive())
                .walletId((user.getWallet() != null)? user.getWallet().getWalletId() : null )
                .build();
    }

    public List<UserDTO> mapToUserDtoList(final List<User> userList) {
        return userList.stream()
                .map(this::mapToUserDto)
                .toList();
    }
}
