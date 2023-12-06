package com.projekt.VirtualBetting.service;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.domains.User;
import com.projekt.VirtualBetting.model.dto.UserDTO;
import com.projekt.VirtualBetting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) throws DomainNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new DomainNotFoundException("User"));
    }

    public User saveUser(final User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, UserDTO userDTO) throws DomainNotFoundException {
        User existingUser = findUserById(id);


        existingUser.setUsername(userDTO.getUsername());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setFirstname(userDTO.getFirstname());
        existingUser.setLastname(userDTO.getLastname());
        existingUser.setDateOfBirth(userDTO.getDateOfBirth());
        existingUser.setCountry(userDTO.getCountry());
        existingUser.setCity(userDTO.getCity());
        existingUser.setIsActive(userDTO.getIsActive());


        return userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}