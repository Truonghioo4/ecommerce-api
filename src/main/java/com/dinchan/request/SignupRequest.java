package com.dinchan.request;

import lombok.Data;

@Data
public class SignupRequest {
    private String email;
    private String otp;
    private String fullName;
}
