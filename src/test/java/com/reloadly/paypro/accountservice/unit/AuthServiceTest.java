package com.reloadly.paypro.accountservice.unit;

import com.reloadly.paypro.accountservice.exceptions.BadRequestException;
import com.reloadly.paypro.accountservice.payload.request.SignupRequest;
import com.reloadly.paypro.accountservice.persistence.model.User;
import com.reloadly.paypro.accountservice.persistence.repository.UserRepository;
import com.reloadly.paypro.accountservice.service.impl.AuthServiceImpl;
import com.reloadly.paypro.accountservice.utils.TestModels;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthServiceImpl authService;


    @Test
    void shouldThrowErrorWhenAttemptingToSignupWithoutProvidingEmail(){
        SignupRequest signupRequest = TestModels.createSignupRequestWithoutEmail();
        assertThrows(BadRequestException.class, () -> {
            authService.processSignup(signupRequest);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowErrorWhenAttemptingToSignupWithoutProvidingUsername(){
        SignupRequest signupRequest = TestModels.createSignupRequestWithoutUsername();
        assertThrows(BadRequestException.class, () -> {
            authService.processSignup(signupRequest);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowErrorWhenAttemptingToSignupWithoutProvidingPassword(){
        SignupRequest signupRequest = TestModels.createSignupRequestWithoutPassword();
        assertThrows(BadRequestException.class, () -> {
            authService.processSignup(signupRequest);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowErrorWhenAttemptingToSignupWithoutProvidingPhoneNumber(){
        SignupRequest signupRequest = TestModels.createSignupRequestWithoutPhoneNumber();
        assertThrows(BadRequestException.class, () -> {
            authService.processSignup(signupRequest);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowErrorWhenAttemptingToCreateUserWithExistingEmail(){
        SignupRequest signupRequest = TestModels.createSignupRequest();
        given(userRepository.existsByEmail(signupRequest.getEmail())).willReturn(true);
        assertThrows(BadRequestException.class, () -> {
            authService.processSignup(signupRequest);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowErrorWhenAttemptingToCreateUserWithExistingUsername(){
        SignupRequest signupRequest = TestModels.createSignupRequest();
        given(userRepository.existsByUsername(signupRequest.getUsername())).willReturn(true);
        assertThrows(BadRequestException.class, () -> {
            authService.processSignup(signupRequest);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowErrorWhenAttemptingToCreateUserWithExistingPhoneNumber(){
        SignupRequest signupRequest = TestModels.createSignupRequest();
        given(userRepository.existsByPhoneNumber(signupRequest.getPhoneNumber())).willReturn(true);
        assertThrows(BadRequestException.class, () -> {
            authService.processSignup(signupRequest);
        });

        verify(userRepository, never()).save(any(User.class));
    }

}
