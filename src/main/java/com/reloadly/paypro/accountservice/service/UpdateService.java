package com.reloadly.paypro.accountservice.service;

import com.reloadly.paypro.accountservice.payload.request.UpdateRequest;

public interface UpdateService {

    String processCustomerDetailsUpdate(String accountNumber, UpdateRequest updateRequest);
}
