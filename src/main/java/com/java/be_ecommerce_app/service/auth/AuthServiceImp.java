package com.java.be_ecommerce_app.service.auth;

import com.java.be_ecommerce_app.config.security.jwt.JwtProvider;
import com.java.be_ecommerce_app.model.dto.request.auth.LoginDto;
import com.java.be_ecommerce_app.model.dto.request.auth.RegisterDto;
import com.java.be_ecommerce_app.model.dto.response.JwtResponse;
import com.java.be_ecommerce_app.model.entity.User;
import com.java.be_ecommerce_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Service
public class AuthServiceImp implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtProvider jwtProvider;



    @Override
    public User register(RegisterDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        User user = User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword()) // cho nhanh luc test
                .email(dto.getEmail())
                .build();

        return userRepository.save(user); // Placeholder for actual save operation
    }
    @Override
    public JwtResponse login(LoginDto dto) {
        User user = userRepository.findByEmail(dto.getEmail());
//        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
//            throw new RuntimeException("Invalid username or password");
        //  }

        // test nhanh
        if (user == null ) {
            throw new RuntimeException("Invalid username or password");
        }
        String token = jwtProvider.generateToken(user.getUsername());
        return JwtResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .token(token)
                .build();
    }
    @Override

    public User getCurrentUser(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName());
    }
}