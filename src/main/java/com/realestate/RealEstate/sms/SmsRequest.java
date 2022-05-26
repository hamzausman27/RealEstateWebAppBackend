package com.realestate.RealEstate.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

public class SmsRequest {

    @NotNull
    private final String phoneNumber; //destination or client

    public SmsRequest(@JsonProperty("phoneNumber") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "SmsRequest{" +
                "phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
