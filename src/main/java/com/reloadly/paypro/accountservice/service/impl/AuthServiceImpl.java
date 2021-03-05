package com.reloadly.paypro.accountservice.service.impl;

import com.reloadly.paypro.accountservice.constant.EventTopicConstant;
import com.reloadly.paypro.accountservice.exceptions.BadRequestException;
import com.reloadly.paypro.accountservice.exceptions.UnauthorisedAccessException;
import com.reloadly.paypro.accountservice.messaging.EventManager;
import com.reloadly.paypro.accountservice.payload.event.UserCreationEvent;
import com.reloadly.paypro.accountservice.payload.request.LoginRequest;
import com.reloadly.paypro.accountservice.payload.request.SignupRequest;
import com.reloadly.paypro.accountservice.payload.response.LoginResponse;
import com.reloadly.paypro.accountservice.persistence.model.User;
import com.reloadly.paypro.accountservice.persistence.repository.UserRepository;
import com.reloadly.paypro.accountservice.security.JwtUtils;
import com.reloadly.paypro.accountservice.service.AuthService;
import com.reloadly.paypro.accountservice.utils.JsonUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Repository
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    EventManager eventManager;


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

        String accountNumber = signupRequest.getPhoneNumber().substring(1);

        User user = new User(signupRequest.getEmail(), signupRequest.getUsername(), passwordEncoder.encode(signupRequest.getPassword()), signupRequest.getPhoneNumber(), accountNumber);
        userRepository.save(user);
        UserCreationEvent userCreationEvent = new UserCreationEvent(user.getEmail(), user.getUsername(), user.getPhoneNumber(), user.getAccountNumber());
        String userCreationEventString = JsonUtil.toJsonString(userCreationEvent);
        eventManager.publishEvent(EventTopicConstant.USER_CREATION, userCreationEventString);
        return "Congratulations, your account number is " + accountNumber;
    }

    @Override
    public LoginResponse processLogin(LoginRequest loginRequest) {
        if (ObjectUtils.isEmpty(loginRequest.getUsername()) || ObjectUtils.isEmpty(loginRequest.getPassword())) {
            throw new BadRequestException("Missing required details");
        }
        LoginResponse response = new LoginResponse();
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            System.out.println(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            response = new LoginResponse(jwt);
        } catch (Exception e){
            throw new UnauthorisedAccessException("Invalid username or password");
        }
        return response;
    }
}
