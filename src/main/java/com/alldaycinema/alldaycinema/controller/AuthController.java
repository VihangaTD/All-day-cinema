package com.alldaycinema.alldaycinema.controller;

import com.alldaycinema.alldaycinema.dto.request.*;
import com.alldaycinema.alldaycinema.dto.response.EmailValidationResponse;
import com.alldaycinema.alldaycinema.dto.response.LoginResponse;
import com.alldaycinema.alldaycinema.dto.response.MessageResponse;
import com.alldaycinema.alldaycinema.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> signup(@Valid @RequestBody UserRequest userRequest){
        return ResponseEntity.ok(authService.signup(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        LoginResponse response = authService.login(loginRequest.getEmail(),loginRequest.getPassword());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate-email")
    public ResponseEntity<EmailValidationResponse> validateEmail(@RequestParam String email){
        return ResponseEntity.ok(authService.validateEmail((email)));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<MessageResponse> verifyEmail(@RequestParam String token){
        return ResponseEntity.ok(authService.verifyEmail(token));
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<MessageResponse> resendVerification(@Valid @RequestBody EmailRequest emailRequest){
        return ResponseEntity.ok(authService.resendVerification(emailRequest.getEmail()));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponse> forgotPassword(@Valid @RequestBody EmailRequest emailRequest){
        return ResponseEntity.ok(authService.forgotPassword(emailRequest.getEmail()));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponse> resetPassword(@Valid @RequestBody ResetPassword resetPassword){
        return ResponseEntity.ok(authService.resetPassword(resetPassword.getToken(),resetPassword.getNewPassword()));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            Authentication authentication,
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest){

        String email = authentication.getName();

        return ResponseEntity.ok(authService.changePassword(
                email,
                changePasswordRequest.getCurrentPassword(),
                changePasswordRequest.getNewPassword()
        ));
    }

    @GetMapping("/current-user")
    public ResponseEntity<LoginResponse> currentUser(Authentication authentication){
        String email = authentication.getName();
        return ResponseEntity.ok(authService.currentUser(email));
    }
}
