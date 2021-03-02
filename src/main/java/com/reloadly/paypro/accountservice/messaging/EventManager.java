package com.reloadly.paypro.accountservice.messaging;

public interface EventManager {

    void publishEvent(String topic, String payload);

}
