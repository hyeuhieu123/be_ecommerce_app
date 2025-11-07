package com.java.be_ecommerce_app.controller;

import com.java.be_ecommerce_app.model.dto.request.auth.LoginDto;
import com.java.be_ecommerce_app.model.dto.request.auth.RegisterDto;
import com.java.be_ecommerce_app.model.dto.request.auth.UpdateProfileDto;
import com.java.be_ecommerce_app.model.dto.response.ApiResponse;
import com.java.be_ecommerce_app.model.dto.response.JwtResponse;
import com.java.be_ecommerce_app.model.entity.User;
import com.java.be_ecommerce_app.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@Valid @RequestBody RegisterDto registerDto) {
        User user = authService.register(registerDto);
        return new ResponseEntity<>(new ApiResponse<>(true,"dang ky thanh cong",user,null), HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(@Valid @RequestBody LoginDto loginDto) {
        System.out.println(loginDto);
        JwtResponse jwtResponse = authService.login(loginDto);
        if (jwtResponse != null) {
            return new ResponseEntity<>(new ApiResponse<>(true, "dang nhap thanh cong ", jwtResponse, null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse<>(false, "Invalid username or password", null, null), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<User>> getCurrentUser(Authentication authentication) {
        User user = authService.getCurrentUser(authentication);
        return new ResponseEntity<>(new ApiResponse<>(true, "User retrieved successfully", user, null), HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<User>> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateProfileDto updateProfileDto) {
        User user = authService.updateProfile(authentication, updateProfileDto);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Profile updated successfully", user, null),
                HttpStatus.OK);
    }
}