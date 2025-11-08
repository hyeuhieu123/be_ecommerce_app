package com.java.be_ecommerce_app.service.auth;

import com.java.be_ecommerce_app.config.security.jwt.JwtProvider;
import com.java.be_ecommerce_app.model.dto.request.auth.LoginDto;
import com.java.be_ecommerce_app.model.dto.request.auth.RegisterDto;
import com.java.be_ecommerce_app.model.dto.request.auth.UpdateProfileDto;
import com.java.be_ecommerce_app.model.dto.response.JwtResponse;
import com.java.be_ecommerce_app.model.entity.User;
import com.java.be_ecommerce_app.repository.UserRepository;
import com.java.be_ecommerce_app.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImp implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CartService cartService;



    @Override
    @Transactional
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
                .fullName("")
                .email(dto.getEmail())
                .build();

        user = userRepository.save(user);
        
        // Auto-create Cart for new user
        cartService.createCartForUser(user.getUserId());
        
        return user;
    }
    @Override
    public JwtResponse login(LoginDto dto) {
        User user = userRepository.findByEmail(dto.getEmail());
        if (user == null || ! (dto.getPassword().equals(user.getPassword())) ) {
            throw new RuntimeException("Invalid username or password");
          }

        // test nhanh

        String token = jwtProvider.generateToken(user.getUsername());
        return JwtResponse.builder()
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .token(token)
                .build();
    }
    @Override
    public User getCurrentUser(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName());
    }

    @Override
    public User updateProfile(Authentication authentication, UpdateProfileDto dto) {
        User user = userRepository.findByUsername(authentication.getName());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Update fullName
        if (dto.getFullName() != null && !dto.getFullName().trim().isEmpty()) {
            user.setFullName(dto.getFullName().trim());
        }

        // Update email
        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            // Check if email is already taken by another user
            User existingUser = userRepository.findByEmail(dto.getEmail());
            if (existingUser != null && !existingUser.getUserId().equals(user.getUserId())) {
                throw new RuntimeException("Email already exists");
            }
            user.setEmail(dto.getEmail());
        }

        // Update phone number
        if (dto.getPhoneNumber() != null) {
            user.setPhoneNumber(dto.getPhoneNumber());
        }

        // Update address
        if (dto.getAddress() != null) {
            user.setAddress(dto.getAddress());
        }

        return userRepository.save(user);
    }
}