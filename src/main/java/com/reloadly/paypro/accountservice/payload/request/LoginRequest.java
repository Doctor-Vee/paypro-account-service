package com.reloadly.paypro.accountservice.payload.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Login Request")
public class LoginRequest {

    private String username;

    private String password;

}
