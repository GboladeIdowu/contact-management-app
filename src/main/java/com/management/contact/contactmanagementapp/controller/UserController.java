package com.management.contact.contactmanagementapp.controller;

import com.management.contact.contactmanagementapp.dto.LoginDTO;
import com.management.contact.contactmanagementapp.dto.UserUpdateDTO;
import com.management.contact.contactmanagementapp.model.User;
import com.management.contact.contactmanagementapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("{email}")
    public ResponseEntity<String> findUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        return userService.userLogin(loginDTO);
    }

    @PostMapping("new")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("update/{email}")
    public ResponseEntity<String> updateUser(@PathVariable String email, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        return userService.updateUser(email, userUpdateDTO);
    }

    @DeleteMapping("delete/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        return userService.deleteUser(email);
    }
}
