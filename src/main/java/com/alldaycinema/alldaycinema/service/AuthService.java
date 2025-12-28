package com.alldaycinema.alldaycinema.service;

import com.alldaycinema.alldaycinema.dto.request.UserRequest;
import com.alldaycinema.alldaycinema.dto.response.LoginResponse;
import com.alldaycinema.alldaycinema.dto.response.MessageResponse;
import jakarta.validation.Valid;

public interface AuthService {
    MessageResponse signup(@Valid UserRequest userRequest);

    LoginResponse login(String email, String password);
}
