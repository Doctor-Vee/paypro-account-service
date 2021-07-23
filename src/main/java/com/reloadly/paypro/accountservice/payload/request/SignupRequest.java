package com.reloadly.paypro.accountservice.payload.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Signup Request")
public class SignupRequest {

    private String email;

    private String username;

    private String password;

    @ApiModelProperty(notes = "The 11 digit phone number e.g. 08081234567")
    private String phoneNumber;

}
