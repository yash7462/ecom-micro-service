package com.auth.service;

import com.auth.config.ApiResponse;
import com.auth.config.JwtProvider;
import com.auth.dto.LoginRequestPayload;
import com.auth.dto.TokenRequestPayload;
import com.auth.dto.UserRegistrationPayload;
import com.auth.modal.UserModal;
import com.auth.repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Log
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Override
    public ResponseEntity<ApiResponse> userRegistration(UserRegistrationPayload userRegistrationPayload) {
        ApiResponse apiResponse = null;
        try {
            UserModal userModal = new UserModal();
            userModal.setEmail(userRegistrationPayload.getEmail());
            userModal.setMobile(userRegistrationPayload.getMobile());
            userModal.setUserName(userRegistrationPayload.getUserName());
            String pass = userRegistrationPayload.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(pass);
            userModal.setPassword(hashedPassword);
            UserModal authUser =userRepository.save(userModal);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRegistrationPayload.getUserName(), userRegistrationPayload.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateJwtToken(authentication);

            //String uuidCivicUser = jwtProvider.getUserCivicIdFromJwtToken(jwt);
            //log.warning("UUID----->"+uuidCivicUser);
            apiResponse = new ApiResponse(true, "USER_CREATED", jwt);

        } catch (Exception e) {
            e.printStackTrace();
            apiResponse = new ApiResponse(false, "Something Went Wrong!", null);
        }
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponse> loginUser(LoginRequestPayload loginRequestPayload) {
        ApiResponse apiResponse = null;
        try {
            UserModal userModal = userRepository.findByUserName(loginRequestPayload.getUserName());
            if(userModal !=null) {
                BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
                boolean passwordChecker = bc.matches(loginRequestPayload.getPassword(), userModal.getPassword());
                log.warning("passwordChecker-------->"+passwordChecker);
                if(passwordChecker) {
                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(loginRequestPayload.getUserName(),loginRequestPayload.getPassword()));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String jwt = jwtProvider.generateJwtToken(authentication);
                    //String uuidCivicUser = jwtProvider.getUserCivicIdFromJwtToken(jwt);
                    //log.warning("UUID----->"+uuidCivicUser);
                    apiResponse = new ApiResponse(true, "Login Success", jwt);

                }else {
                    apiResponse = new ApiResponse(false, "Invalid Credentials", null);
                }
            }else {
                apiResponse = new ApiResponse(false, "User Not Found", null);
            }
        } catch (Exception e) {
            apiResponse = new ApiResponse(false, "Something Went Wrong!", null);
        }
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponse> validateToken(String token) {
        ApiResponse apiResponse = null;
        try {
            UUID userId = jwtProvider.getUserIdFromJwtToken(token);
            UserModal userModal = userRepository.findById(userId).orElse(null);
            if(userId !=null) {
                apiResponse = new ApiResponse(true, "Success", userId);
            }else {
                apiResponse = new ApiResponse(false, "Invalid Token", null);
            }
        } catch (Exception e) {
            apiResponse = new ApiResponse(false, "Something Went Wrong!", null);
        }
        return ResponseEntity.ok(apiResponse);
    }
}
