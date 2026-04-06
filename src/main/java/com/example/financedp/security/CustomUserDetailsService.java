package com.example.financedp.security;

import com.example.financedp.exception.ResourceNotFoundException;
import com.example.financedp.model.User;
import com.example.financedp.repository.UserRepository;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not Found")
                );
        return new CustomUserDetails(user);
    }

}
