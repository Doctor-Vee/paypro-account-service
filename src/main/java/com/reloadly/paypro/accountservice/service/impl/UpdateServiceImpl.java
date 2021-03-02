package com.reloadly.paypro.accountservice.service.impl;

import com.reloadly.paypro.accountservice.constant.EventTopicConstant;
import com.reloadly.paypro.accountservice.exceptions.BadRequestException;
import com.reloadly.paypro.accountservice.exceptions.UnauthorisedAccessException;
import com.reloadly.paypro.accountservice.messaging.EventManager;
import com.reloadly.paypro.accountservice.payload.event.UserUpdateEvent;
import com.reloadly.paypro.accountservice.payload.request.UpdateRequest;
import com.reloadly.paypro.accountservice.persistence.model.User;
import com.reloadly.paypro.accountservice.persistence.repository.UserRepository;
import com.reloadly.paypro.accountservice.service.UpdateService;
import com.reloadly.paypro.accountservice.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
public class UpdateServiceImpl implements UpdateService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventManager eventManager;

    @Override
    public String processCustomerDetailsUpdate(String accountNumber, UpdateRequest updateRequest) {
        Optional<User> userOptional = userRepository.findByAccountNumber(accountNumber);
        if(userOptional.isEmpty()){
            throw new UnauthorisedAccessException("Unauthorised Access - User not found");
        }
        User user = userOptional.get();
        UserUpdateEvent updateEvent = new UserUpdateEvent();
        updateEvent.setEmail(user.getEmail());
        if(!ObjectUtils.isEmpty(updateRequest.getUsername())){
            if (userRepository.existsByUsername(updateRequest.getUsername())) throw new BadRequestException("Username already exists");
            user.setUsername(updateRequest.getUsername());
            updateEvent.setUsername(updateRequest.getUsername());
        }
        if(!ObjectUtils.isEmpty(updateRequest.getPhoneNumber())){
            if(userRepository.existsByPhoneNumber(updateRequest.getPhoneNumber())) throw new BadRequestException("Phone number already exists");
            user.setPhoneNumber(updateRequest.getPhoneNumber());
            updateEvent.setPhoneNumber(updateRequest.getPhoneNumber());
        }
        userRepository.save(user);
        String updateEventString = JsonUtil.toJsonString(updateEvent);
        eventManager.publishEvent(EventTopicConstant.USER_UPDATE, updateEventString);
        return "Details updated successfully";
    }
}
