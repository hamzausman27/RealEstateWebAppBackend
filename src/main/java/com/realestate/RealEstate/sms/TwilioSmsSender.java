package com.realestate.RealEstate.sms;

import com.realestate.RealEstate.sms.passcode.PasscodeVerification;
import com.realestate.RealEstate.sms.passcode.PasscodeVerificationService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.realestate.RealEstate.utils.AppConstants.PASS_CODE_MESSAGE;

@AllArgsConstructor
@Service("twilio")
public class TwilioSmsSender implements SmsSender{

    private static final Logger logger = LoggerFactory.getLogger(TwilioSmsSender
    .class);
    private final TwilioConfiguration twilioConfiguration;
    private final PasscodeVerificationService passcodeVerificationService;

//
//    @Autowired
//    public TwilioSmsSender(TwilioConfiguration twilioConfiguration, PasscodeVerificationService passcodeVerificationService) {
//        this.twilioConfiguration = twilioConfiguration;
//        this.passcodeVerificationService = passcodeVerificationService;
//    }

    @Override
    public void sendSms(SmsRequest smsRequest) {

        if(isPhoneValid(smsRequest.getPhoneNumber())){
        PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
        PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());

        String passcode = RandomStringUtils.randomNumeric(6);
        String message = PASS_CODE_MESSAGE+ passcode;

        MessageCreator creator = Message.creator(to,from,message);
        creator.create();
        logger.info("send sms {}" , smsRequest + " " + message);

        //save passcode in db?
            passcodeVerificationService.savePassCode(
                    new PasscodeVerification(smsRequest.getPhoneNumber(),passcode)
            );

        }else{
            throw new IllegalStateException("pHONE NUMBER IS NOT VALUD!!" + smsRequest.getPhoneNumber());
        }

    }

    private boolean isPhoneValid(String phoneNumber) {
//    TODO : VALIDATE PHONE NUMBER

        return true;
    }
}
