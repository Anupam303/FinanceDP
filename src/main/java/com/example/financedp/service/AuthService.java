package com.example.financedp.service;

import com.example.financedp.exception.BadRequestException;
import com.example.financedp.exception.ResourceNotFoundException;
import com.example.financedp.model.Status;
import com.example.financedp.model.User;
import com.example.financedp.repository.UserRepository;
import com.example.financedp.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthService(PasswordEncoder encoder, JwtUtil jwtUtil, UserRepository userRepository) {
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public String login(String username, String password){

        User user = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Username not found")
                );

        if(!encoder.matches(password,user.getPassword())){
            throw new BadRequestException("Invalid password");
        }
        System.out.println("RAW PASSWORD: " + password);
        System.out.println("DB PASSWORD: " + user.getPassword());
        System.out.println("MATCH RESULT: " + encoder.matches(password, user.getPassword()));
        user.setStatus(Status.ACTIVE);
        user.setLastActiveAt(LocalDateTime.now());

        userRepository.save(user);

        return jwtUtil.generate(user.getUsername());
    }
}
