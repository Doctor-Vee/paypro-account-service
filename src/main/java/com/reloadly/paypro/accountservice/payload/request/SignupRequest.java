package com.reloadly.paypro.accountservice.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class SignupRequest {

    private String email;

    private String username;

    private String password;

    private String phoneNumber;

}
