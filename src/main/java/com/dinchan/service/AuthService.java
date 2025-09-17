package com.dinchan.service;

import com.dinchan.domain.USER_ROLE;
import com.dinchan.request.LoginRequest;
import com.dinchan.request.SignupRequest;
import com.dinchan.response.AuthResponse;

public interface AuthService {
    void sentLoginOtp(String email, USER_ROLE role) throws Exception;
    String createUser(SignupRequest req) throws Exception;
    AuthResponse signing(LoginRequest req) throws Exception;
}
