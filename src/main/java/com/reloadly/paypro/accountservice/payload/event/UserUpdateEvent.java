package com.reloadly.paypro.accountservice.payload.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateEvent {

    private String email;

    private String username;

    private String phoneNumber;

}
