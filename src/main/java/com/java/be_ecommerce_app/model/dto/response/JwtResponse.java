package com.java.be_ecommerce_app.model.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String token;
}