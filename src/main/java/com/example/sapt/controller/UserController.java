package com.example.sapt.controller;

import com.example.sapt.cutomDTO.UpdateRequest;
import com.example.sapt.cutomDTO.UserUpdateRequest;
import com.example.sapt.dto.UserDTO;
import com.example.sapt.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/{id}")
    public UserDTO updateUser(@PathVariable long id, @RequestBody UserUpdateRequest updateRequest) {
        return userService.updateUser(id, updateRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}
