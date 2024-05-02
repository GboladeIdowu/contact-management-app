package com.management.contact.contactmanagementapp.service;

import com.management.contact.contactmanagementapp.dto.UserDto;
import com.management.contact.contactmanagementapp.model.User;
import com.management.contact.contactmanagementapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    // Find user by email
    public ResponseEntity<User> findUserByEmail(String email){
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("User with email: %s not found", email));
        }
        return new ResponseEntity<>(user, HttpStatus.FOUND);
    }

    // Create new user
    public ResponseEntity<String> createUser(User user){
        User createUser = userRepository.findByEmail(user.getEmail());
        if(createUser != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
        userRepository.save(user);
        return new ResponseEntity<>("Created successfully",HttpStatus.CREATED);
    }

    // Update existing user details
    public ResponseEntity<String> updateUser(String email, UserDto userDto){
        User existingUser = userRepository.findByEmail(email);
        if(existingUser == null){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,
                    String.format("User with email: %s not found!\nUpdate failed.",email));
        }
        existingUser.setFirstName(userDto.firstName());
        existingUser.setLastName(userDto.lastName());
        userRepository.save(existingUser);
        return new ResponseEntity<>("Update successful", HttpStatus.OK);
    }

    // Delete user by email
    public ResponseEntity<String> deleteUser(String email){
        User existingUser = userRepository.findByEmail(email);
        if(existingUser == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        userRepository.delete(existingUser);
        return new ResponseEntity<>("User deleted",HttpStatus.OK);
    }
}
