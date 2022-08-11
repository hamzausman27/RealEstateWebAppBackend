package com.realestate.RealEstate.sms;

public interface SmsSender {

    void sendSms(SmsRequest sendRequest);
    boolean verifyPhoneNumber(String passcode,String phoneNumber);
}
