package com.reloadly.paypro.accountservice.controllers;

import com.reloadly.paypro.accountservice.payload.request.LoginRequest;
import com.reloadly.paypro.accountservice.payload.request.SignupRequest;
import com.reloadly.paypro.accountservice.payload.response.LoginResponse;
import com.reloadly.paypro.accountservice.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@Api(tags = {"Authentication Controller"})
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    @ApiOperation(value = "The endpoint for signing up as a new user",
            notes = "This endpoint checks if the username, phone number or email already exists before attempting to create the new user")
    public ResponseEntity<String> registerUser(@RequestBody SignupRequest signupRequest){
        String response = authService.processSignup(signupRequest);
        return ResponseEntity.created(URI.create("")).body(response);
    }

    @PostMapping("/login")
    @ApiOperation(value = "The login endpoint for existing users")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest){
        LoginResponse response = authService.processLogin(loginRequest);
        return ResponseEntity.ok(response);
    }

}

