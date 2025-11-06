package com.java.be_ecommerce_app.model.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

    @NotNull(message = "Username cannot be empty")
    private String username;
    @NotNull(message = "Password cannot be empty" )
    private String password;
//    @NotNull(message = "Confirm password does not match")
//    private String confirmPassword;
    @Email(message = "Email should be valid")
    @NotNull(message = "Email cannot be empty")
    private String email;



}