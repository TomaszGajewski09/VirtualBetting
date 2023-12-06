package com.projekt.VirtualBetting.mapper;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.domains.User;
import com.projekt.VirtualBetting.model.domains.Wallet;
import com.projekt.VirtualBetting.model.dto.UserDTO;
import com.projekt.VirtualBetting.service.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    @Mock
    private WalletMapper walletMapper;
    @Mock
    private WalletService walletService;

    @InjectMocks
    private UserMapper userMapper;

    @Test
    void mapToUser_ShouldMapCorrectly() throws DomainNotFoundException {
        // GIVEN
        UserDTO userDto = new UserDTO();
        userDto.setUserid(1l);
        userDto.setUsername("user");
        userDto.setWalletId(1l);

        // WHEN
        User result = userMapper.mapToUser(userDto);

        // THEN
        assertNotNull(result);
        assertEquals(userDto.getUsername(), result.getUsername());

    }

    @Test
    void mapToUserDto_ShouldMapCorrectly() {
        // GIVEN
        User user = new User();


        Wallet wallet = new Wallet();
        user.setWallet(wallet);

        // WHEN
        UserDTO result = userMapper.mapToUserDto(user);

        // THEN
        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
        // More assertions to verify other properties are mapped correctly
    }

    @Test
    void mapToUserDtoList_ShouldMapListCorrectly() {
        // GIVEN
        User user1 = new User();
        User user2 = new User();
        // Initialize users as needed

        List<User> users = Arrays.asList(user1, user2);

        // WHEN
        List<UserDTO> result = userMapper.mapToUserDtoList(users);

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        // Additional assertions as needed
    }
}

