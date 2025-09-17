package com.dinchan.controller;

import com.dinchan.domain.USER_ROLE;
import com.dinchan.model.User;
import com.dinchan.model.VerificationCode;
import com.dinchan.repository.UserRepository;
import com.dinchan.request.LoginOtpRequest;
import com.dinchan.request.LoginRequest;
import com.dinchan.request.SignupRequest;
import com.dinchan.response.ApiResponse;
import com.dinchan.response.AuthResponse;
import com.dinchan.service.AuthService;
import com.dinchan.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUser(@RequestBody SignupRequest req) throws Exception {
        String jwt= authService.createUser(req);
        AuthResponse res= new AuthResponse();
        res.setJwt(jwt);
        res.setMessage("register success");
        res.setRole(USER_ROLE.ROLE_CUSTOMER);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) throws Exception {
        AuthResponse authResponse = authService.signing(req);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/sent-otp")
    public ResponseEntity<ApiResponse> sentOtp(@RequestBody LoginOtpRequest req) throws Exception {
        authService.sentLoginOtp(req.getEmail(), req.getRole());
        ApiResponse res= new ApiResponse();
        res.setMessage("otp sent successfully");
        return ResponseEntity.ok(res);
    }

}
