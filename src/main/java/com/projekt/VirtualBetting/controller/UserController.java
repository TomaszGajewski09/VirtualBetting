package com.projekt.VirtualBetting.controller;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.mapper.UserMapper;
import com.projekt.VirtualBetting.model.domains.User;
import com.projekt.VirtualBetting.model.dto.UserDTO;
import com.projekt.VirtualBetting.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private UserMapper userMapper;
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userMapper.mapToUserDtoList(userService.findAllUsers()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) throws DomainNotFoundException {
        User user = userService.findUserById(id);
        return ResponseEntity.ok(userMapper.mapToUserDto(user));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = userMapper.mapToUser(userDTO);
        User createdUser = userService.saveUser(user);
        return ResponseEntity.ok(userMapper.mapToUserDto(createdUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDetails) throws DomainNotFoundException {
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(userMapper.mapToUserDto(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
