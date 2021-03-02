package com.reloadly.paypro.accountservice.payload.event;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreationEvent {

    private String email;

    private String username;

    private String phoneNumber;

    private String accountNumber;

}
