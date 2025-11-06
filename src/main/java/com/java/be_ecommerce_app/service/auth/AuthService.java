package com.java.be_ecommerce_app.service.auth;

import com.java.be_ecommerce_app.model.dto.request.auth.LoginDto;
import com.java.be_ecommerce_app.model.dto.request.auth.RegisterDto;
import com.java.be_ecommerce_app.model.dto.response.JwtResponse;
import com.java.be_ecommerce_app.model.entity.User;
import org.springframework.security.core.Authentication;


public interface AuthService  {
    User register(RegisterDto dto);
    JwtResponse login(LoginDto dto);
    User getCurrentUser(Authentication authentication);
}