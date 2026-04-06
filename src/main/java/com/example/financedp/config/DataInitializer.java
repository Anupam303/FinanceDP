package com.example.financedp.config;

import com.example.financedp.model.Role;
import com.example.financedp.model.Status;
import com.example.financedp.model.User;
import com.example.financedp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initiateAdmin(UserRepository userRepository, PasswordEncoder encoder){
        return args -> {
            String username = "admin";
            if(userRepository.findByUsername(username).isEmpty()){

                User admin = new User();
                admin.setUsername(username);
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                admin.setStatus(Status.ACTIVE);
                admin.setLastActiveAt(LocalDateTime.now());

                userRepository.save(admin);
                System.out.println("Admin created");
            }
        };
    }
}
