package com.dinchan.service;

import com.dinchan.request.LoginRequest;
import com.dinchan.request.SignupRequest;
import com.dinchan.response.AuthResponse;

public interface AuthService {
    String createUser(SignupRequest req) throws Exception;
    AuthResponse login(LoginRequest req) throws Exception;
}
