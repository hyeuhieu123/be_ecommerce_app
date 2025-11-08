package com.java.be_ecommerce_app.model.dto.request.auth;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileDto {
    private String fullName;
    @Email(message = "Email should be valid")
    private String email;
    private String phoneNumber;
    private String address;
    private String gender;
    private String avatarUrl;
}