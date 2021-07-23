package com.reloadly.paypro.accountservice.payload.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Login Response")
public class LoginResponse {

    @ApiModelProperty(notes = "The Bearer token returned after login")
    private String token;

}
