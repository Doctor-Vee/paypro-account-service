package com.reloadly.paypro.accountservice.service.impl;

import com.reloadly.paypro.accountservice.exceptions.BadRequestException;
import com.reloadly.paypro.accountservice.payload.request.LoginRequest;
import com.reloadly.paypro.accountservice.payload.request.SignupRequest;
import com.reloadly.paypro.accountservice.payload.response.LoginResponse;
import com.reloadly.paypro.accountservice.persistence.model.User;
import com.reloadly.paypro.accountservice.persistence.repository.UserRepository;
import com.reloadly.paypro.accountservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;

@Repository
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public String processSignup(SignupRequest signupRequest) {
        if (ObjectUtils.isEmpty(signupRequest.getEmail()) || ObjectUtils.isEmpty(signupRequest.getPassword()) ||
                ObjectUtils.isEmpty(signupRequest.getPhoneNumber()) || ObjectUtils.isEmpty(signupRequest.getUsername())) {
            throw new BadRequestException("Missing required details");
        }

        if (userRepository.existsByUsername(signupRequest.getUsername()))
            throw new BadRequestException("Error: Sorry ... username has been taken ... Choose another one");
        if (userRepository.existsByEmail(signupRequest.getEmail()))
            throw new BadRequestException("Error: Sorry ... email is already in use. Login with this email if it's yours");
        if (userRepository.existsByPhoneNumber(signupRequest.getPhoneNumber()))
            throw new BadRequestException("Error: Sorry ... this phone number has already been registered here");

        String accountNumber = "000" + RandomStringUtils.randomNumeric(7);

        User user = new User(signupRequest.getEmail(), signupRequest.getUsername(), passwordEncoder.encode(signupRequest.getPassword()), signupRequest.getPhoneNumber(), accountNumber, BigDecimal.ZERO);
        userRepository.save(user);
        return "Congratulations, your account number is " + accountNumber;
    }

    @Override
    public LoginResponse processLogin(LoginRequest loginRequest) {
        return null;
    }
}
