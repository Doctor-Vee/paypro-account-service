package com.reloadly.paypro.accountservice.payload.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;

    private String password;

}
