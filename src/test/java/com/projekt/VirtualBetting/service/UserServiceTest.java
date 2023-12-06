package com.projekt.VirtualBetting.service;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.domains.User;
import com.projekt.VirtualBetting.model.dto.UserDTO;
import com.projekt.VirtualBetting.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void findAllUsers_ShouldReturnAllUsers() {
        // GIVEN
        User user1 = new User();
        User user2 = new User();
        List<User> expectedUsers = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // WHEN
        List<User> result = userService.findAllUsers();

        // THEN
        assertEquals(expectedUsers, result);
        verify(userRepository).findAll();
    }

    @Test
    void findUserById_ShouldReturnUser() throws DomainNotFoundException {
        // GIVEN
        Long id = 1L;
        User expectedUser = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(expectedUser));

        // WHEN
        User result = userService.findUserById(id);

        // THEN
        assertEquals(expectedUser, result);
        verify(userRepository).findById(id);
    }

    @Test
    void findUserById_ShouldThrowDomainNotFoundException() {
        // GIVEN
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // THEN
        assertThrows(DomainNotFoundException.class, () -> {
            // WHEN
            userService.findUserById(id);
        });
    }

    @Test
    void saveUser_ShouldReturnSavedUser() {
        // GIVEN
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        // WHEN
        User result = userService.saveUser(user);

        // THEN
        assertEquals(user, result);
        verify(userRepository).save(user);
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() throws DomainNotFoundException {
        // GIVEN
        Long id = 1L;
        User existingUser = new User();
        UserDTO userDTO = new UserDTO();
        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        // WHEN
        User result = userService.updateUser(id, userDTO);

        // THEN
        assertNotNull(result);
        verify(userRepository).findById(id);
        verify(userRepository).save(existingUser);
    }

    @Test
    void deleteUser_ShouldInvokeDelete() {
        // GIVEN
        Long id = 1L;

        // WHEN
        userService.deleteUser(id);

        // THEN
        verify(userRepository).deleteById(id);
    }

    @Test
    void deleteAll_ShouldInvokeDeleteAll() {
        // WHEN
        userService.deleteAll();

        // THEN
        verify(userRepository).deleteAll();
    }
}
