package com.example.financedp.service;

import com.example.financedp.exception.BadRequestException;
import com.example.financedp.exception.ResourceNotFoundException;
import com.example.financedp.model.Status;
import com.example.financedp.model.User;
import com.example.financedp.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public User create(User user){
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new BadRequestException("Username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);
        user.setLastActiveAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public User update(Long id, User user){
        User userToUpdate =userRepository.findById(id)
                .orElseThrow(
                        () ->new ResourceNotFoundException("User not found")
                );
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setRole(user.getRole());
        userToUpdate.setStatus(user.getStatus());

        return userRepository.save(userToUpdate);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id){
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("User not Found");
        }
        userRepository.deleteById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST')")
    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();

        if(users.isEmpty()){
            throw new ResourceNotFoundException("no users found");
        }

        return users;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST')")
    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not Found")
                );
    }
}
