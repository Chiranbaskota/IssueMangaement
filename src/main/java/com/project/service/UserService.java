package com.project.service;

import com.project.dto.auth.RegisterRequest;
import com.project.dto.auth.UserResponse;
import com.project.entity.User;

public interface UserService {

    UserResponse registerUser(RegisterRequest registerRequest);

    User findByUsername(String username);
}
