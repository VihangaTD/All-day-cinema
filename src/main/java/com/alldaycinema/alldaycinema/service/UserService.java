package com.alldaycinema.alldaycinema.service;

import com.alldaycinema.alldaycinema.dto.request.UserRequest;
import com.alldaycinema.alldaycinema.dto.response.MessageResponse;
import com.alldaycinema.alldaycinema.dto.response.PageResponse;
import com.alldaycinema.alldaycinema.dto.response.UserResponse;

public interface UserService {
    MessageResponse createUser(UserRequest userRequest);

    MessageResponse updateUser(Long id, UserRequest userRequest);

    PageResponse<UserResponse> getUsers(int page, int size, String search);

    MessageResponse deleteUser(Long id, String currentUserEmail);

    MessageResponse toggleUserStatus(Long id, String currentUserEmail);

    MessageResponse changeUserRole(Long id, UserRequest userRequest);
}
