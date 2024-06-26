package com.realestate.RealEstate.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private final SmsSender smsSender;

  //  private final AppUserService appUserService;

    @Autowired
    public SmsService(TwilioSmsSender twilioSmsSender) {
        this.smsSender = twilioSmsSender;
    }

    public void sendSms(SmsRequest smsRequest) {
        smsSender.sendSms(smsRequest);
    }

//    public boolean verifyPhoneNumber(VerificationRequest verificationRequest){
//        if(smsSender.verifyPhoneNumber(verificationRequest.getPasscode(),verificationRequest.getPhoneNumber())){
//            appUserService.verifyAppUser(verificationRequest.getPhoneNumber());
//                return true;
//        }
//        return false;
//    }



}
