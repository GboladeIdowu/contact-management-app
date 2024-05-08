package com.management.contact.contactmanagementapp.service;

import com.management.contact.contactmanagementapp.dto.LoginDTO;
import com.management.contact.contactmanagementapp.dto.UserUpdateDTO;
import com.management.contact.contactmanagementapp.model.User;
import com.management.contact.contactmanagementapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    // Find user by email
    public ResponseEntity<String> findUserByEmail(String email){
        User user = userRepository.findByEmail(email);
        if(user == null){
            return new ResponseEntity<>(String.format("User with email: %s does not exist!", email), HttpStatus.OK);
        }
        return new ResponseEntity<>(user.toString(), HttpStatus.OK);
    }

    // login
    public ResponseEntity<String> userLogin(LoginDTO loginDTO){
        User user = userRepository.findByEmail(loginDTO.email());
        if (user == null || !user.getPassword().equals(loginDTO.password())) {
            return  new ResponseEntity<>("Email or password is incorrect", HttpStatus.OK);
        }
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
    }

    // Create new user
    public ResponseEntity<String> createUser(User user){
        User createUser = userRepository.findByEmail(user.getEmail());
        if(createUser != null){
            return new ResponseEntity<>( "User already exists!", HttpStatus.OK);
        }
        userRepository.save(user);
        return new ResponseEntity<>("Created successfully",HttpStatus.CREATED);
    }

    // Update existing user details
    public ResponseEntity<String> updateUser(String email, UserUpdateDTO userUpdateDTO){
        User existingUser = userRepository.findByEmail(email);
        if(existingUser == null){
            return new ResponseEntity<>(
                    String.format("User with email: %s not found!\nUpdate failed.",email), HttpStatus.OK);
        }
        existingUser.setFirstName(userUpdateDTO.firstName());
        existingUser.setLastName(userUpdateDTO.lastName());
        userRepository.save(existingUser);
        return new ResponseEntity<>("Update successful", HttpStatus.OK);
    }

    // Delete user by email
    public ResponseEntity<String> deleteUser(String email){
        User existingUser = userRepository.findByEmail(email);
        if(existingUser == null){
           return  new ResponseEntity<>(HttpStatus.OK);
        }
        userRepository.delete(existingUser);
        return new ResponseEntity<>("User deleted",HttpStatus.OK);
    }
}
