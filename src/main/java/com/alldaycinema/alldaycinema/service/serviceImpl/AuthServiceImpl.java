package com.alldaycinema.alldaycinema.service.serviceImpl;

import com.alldaycinema.alldaycinema.dto.request.UserRequest;
import com.alldaycinema.alldaycinema.dto.response.LoginResponse;
import com.alldaycinema.alldaycinema.dto.response.MessageResponse;
import com.alldaycinema.alldaycinema.entity.User;
import com.alldaycinema.alldaycinema.enums.Role;
import com.alldaycinema.alldaycinema.exception.AccountDeactivatedException;
import com.alldaycinema.alldaycinema.exception.BadCredentialsException;
import com.alldaycinema.alldaycinema.exception.EmailAlreadyExistsException;
import com.alldaycinema.alldaycinema.exception.EmailNotVerifiedException;
import com.alldaycinema.alldaycinema.repo.UserRepository;
import com.alldaycinema.alldaycinema.security.JwtUtil;
import com.alldaycinema.alldaycinema.service.AuthService;
import com.alldaycinema.alldaycinema.service.EmailService;
import com.alldaycinema.alldaycinema.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ServiceUtils serviceUtils;

    @Override
    public MessageResponse signup(UserRequest userRequest) {

        if (userRepository.existsByEmail(userRequest.getEmail())){
            throw new EmailAlreadyExistsException("Email Already exists");
        }
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setFullName(userRequest.getFullName());
        user.setRole(Role.USER);
        user.setActive(true);
        user.setEmailVerified(false);
        String verificationToken = UUID.randomUUID().toString();
        user.setVerificationToken(verificationToken);
        user.setVerificationTokenExpiry(Instant.now().plusSeconds(86400));
        userRepository.save(user);
        emailService.sendVerificationEmail(userRequest.getEmail(), verificationToken);

        return new MessageResponse("Registration successfully! Please check your email to verify your account");
    }

    @Override
    public LoginResponse login(String email, String password) {

        User user = userRepository
                .findByEmail(email)
                .filter(u-> passwordEncoder.matches(password, u.getPassword()))
                .orElseThrow(()->new BadCredentialsException("Invalid email or password"));

        if (!user.isActive()){
            throw new AccountDeactivatedException("Your account has been deactivated. " +
                    "Please contact support for assistance");
        }
        if (!user.isEmailVerified()){
            throw new EmailNotVerifiedException("Please verify your email address before logging in. " +
                    "Check your inbox for thw verification link");
        }

        final String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return new LoginResponse(token,user.getEmail(), user.getFullName(),user.getRole().name() );
    }
}
