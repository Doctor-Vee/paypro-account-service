package com.reloadly.paypro.accountservice.utils;

import com.reloadly.paypro.accountservice.payload.request.LoginRequest;
import com.reloadly.paypro.accountservice.payload.request.SignupRequest;

public class TestModels {

    public static SignupRequest createSignupRequest(){
        return new SignupRequest("ovisco360@gmail.com", "doctorvee", "doctor1234", "08085492459");
    }

    public static SignupRequest createSignupRequestWithoutPhoneNumber(){
        return new SignupRequest("ovisco360@gmail.com", "doctorvee", "doctor1234", null);
    }

    public static SignupRequest createSignupRequestWithoutEmail(){
        return new SignupRequest(null, "doctorvee", "doctor1234", "08085492459");
    }

    public static SignupRequest createSignupRequestWithoutPassword(){
        return new SignupRequest("ovisco360@gmail.com", "doctorvee", null, "08085492459");
    }

    public static SignupRequest createSignupRequestWithoutUsername(){
        return new SignupRequest("ovisco360@gmail.com", null, "doctor1234", "08085492459");
    }

    public static LoginRequest createLoginRequestWithoutUsername(){
        return new LoginRequest(null, "pass1234");
    }

    public static LoginRequest createLoginRequestWithoutPassword(){
        return new LoginRequest("doctorvee", null);
    }
}
