package com.reloadly.paypro.accountservice.controllers;

import com.reloadly.paypro.accountservice.payload.request.UpdateRequest;
import com.reloadly.paypro.accountservice.security.AuthenticatedUserDetails;
import com.reloadly.paypro.accountservice.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/update")
public class UpdateController {
    @Autowired
    UpdateService updateService;

    @PutMapping("")
    public ResponseEntity<String> updateUserDetails(@AuthenticationPrincipal AuthenticatedUserDetails userDetails, @RequestBody UpdateRequest updateRequest) {
        String accountNumber = userDetails.getAccountNumber();
        String response = updateService.processCustomerDetailsUpdate(accountNumber, updateRequest);
        return ResponseEntity.ok(response);
    }
}
