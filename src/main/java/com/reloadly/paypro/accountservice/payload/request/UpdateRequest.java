package com.reloadly.paypro.accountservice.payload.request;

import lombok.Data;

@Data
public class UpdateRequest {

    private String username;

    private String phoneNumber;

}
