package com.reloadly.paypro.accountservice.controllers;

import com.reloadly.paypro.accountservice.payload.request.UpdateRequest;
import com.reloadly.paypro.accountservice.security.AuthenticatedUserDetails;
import com.reloadly.paypro.accountservice.service.UpdateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/update", headers = {"Authorization"})
@Api(tags = {"Update Controller"})
public class UpdateController {
    @Autowired
    UpdateService updateService;

    @PutMapping("")
    @ApiOperation(value = "Update User Details")
    public ResponseEntity<String> updateUserDetails(@ApiIgnore @AuthenticationPrincipal AuthenticatedUserDetails userDetails, @RequestBody UpdateRequest updateRequest) {
        String accountNumber = userDetails.getAccountNumber();
        String response = updateService.processCustomerDetailsUpdate(accountNumber, updateRequest);
        return ResponseEntity.ok(response);
    }
}
