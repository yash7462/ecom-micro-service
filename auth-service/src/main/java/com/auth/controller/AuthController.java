package com.auth.controller;

import com.auth.config.ApiResponse;
import com.auth.dto.TokenRequestPayload;
import com.auth.service.UserService;
import com.auth.utils.EcomConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.auth.config.JwtProvider;
import com.auth.repository.UserRepository;
import com.auth.dto.LoginRequestPayload;
import com.auth.dto.UserRegistrationPayload;
import com.auth.modal.UserModal;

import lombok.extern.java.Log;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Log
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;


	@PostMapping("/signup")
	public ResponseEntity<ApiResponse> userRegistration(@RequestBody UserRegistrationPayload userRegistrationPayload) {
		return userService.userRegistration(userRegistrationPayload);
	}
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse> loginUser(@RequestBody LoginRequestPayload loginRequestPayload) {
		return userService.loginUser(loginRequestPayload);
	}

	@PostMapping("/validate")
	public ResponseEntity<ApiResponse> validateToken(@RequestHeader(name = EcomConstant.AUTH_TOKEN) String token) {
		return userService.validateToken(token);
	}


}
