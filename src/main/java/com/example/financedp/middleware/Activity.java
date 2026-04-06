package com.example.financedp.middleware;

import com.example.financedp.model.Status;
import com.example.financedp.model.User;
import com.example.financedp.repository.UserRepository;
import com.example.financedp.security.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class Activity extends OncePerRequestFilter {

    private UserRepository userRepository;

    public Activity(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{

        try{
            Object principal = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();

            if(principal instanceof CustomUserDetails userDetails){
                 User user = userDetails.getUser();

                     LocalDateTime now = LocalDateTime.now();
                     if (user.getLastActiveAt() != null && user.getLastActiveAt().isBefore(now.minusMinutes(1))) {
                         user.setStatus(Status.INACTIVE);
                     } else {
                         user.setStatus(Status.ACTIVE);
                     }

                     user.setLastActiveAt(now);

                     userRepository.save(user);
                 }
        } catch (Exception e) {

            System.out.println("Activity error:" + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
