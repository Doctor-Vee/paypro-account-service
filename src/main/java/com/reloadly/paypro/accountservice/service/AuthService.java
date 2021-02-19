package com.reloadly.paypro.accountservice.service;

import com.reloadly.paypro.accountservice.payload.request.LoginRequest;
import com.reloadly.paypro.accountservice.payload.request.SignupRequest;
import com.reloadly.paypro.accountservice.payload.response.LoginResponse;

public interface AuthService {

    String processSignup(SignupRequest signupRequest);

    LoginResponse processLogin(LoginRequest loginRequest);

}
