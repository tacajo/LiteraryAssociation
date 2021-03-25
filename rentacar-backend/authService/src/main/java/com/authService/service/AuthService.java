package com.authService.service;

import com.authService.model.TokenValidationResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    void login();

    void register();

    void blockUser();

    void activateUser();

    void removeUser();

    TokenValidationResponse validateToken(String token);
}
