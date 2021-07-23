package com.reloadly.paypro.accountservice.payload.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Update Request")
public class UpdateRequest {

    private String username;

    @ApiModelProperty(notes = "The 11 digit phone number e.g. 08081234567")
    private String phoneNumber;

}
