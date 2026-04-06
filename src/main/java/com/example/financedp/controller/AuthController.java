package com.example.financedp.controller;

import com.example.financedp.model.User;
import com.example.financedp.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User login){

        String token = authService.login(
                login.getUsername(),
                login.getPassword()
        );
        return ResponseEntity.ok(token);
    }
}
