package com.auth.service;

import com.auth.config.ApiResponse;
import com.auth.dto.LoginRequestPayload;
import com.auth.dto.TokenRequestPayload;
import com.auth.dto.UserRegistrationPayload;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ApiResponse> userRegistration(UserRegistrationPayload userRegistrationPayload);

    ResponseEntity<ApiResponse> loginUser(LoginRequestPayload loginRequestPayload);

    ResponseEntity<ApiResponse> validateToken(String token);
}
