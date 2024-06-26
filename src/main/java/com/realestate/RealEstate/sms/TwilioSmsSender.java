package com.realestate.RealEstate.sms;

import com.realestate.RealEstate.sms.passcode.PasscodeVerification;
import com.realestate.RealEstate.sms.passcode.PasscodeVerificationService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.realestate.RealEstate.utils.AppConstants.PASS_CODE_MESSAGE;

@AllArgsConstructor
@Service("veevo")
public class TwilioSmsSender implements SmsSender{

    private static final Logger logger = LoggerFactory.getLogger(TwilioSmsSender
    .class);
    private final PasscodeVerificationService passcodeVerificationService;

    private final VeevoSmsService veevoSmsService;

    @Override
    public void sendSms(SmsRequest smsRequest) {

        if(isPhoneValid(smsRequest.getPhoneNumber())){
        String passcode = RandomStringUtils.randomNumeric(6);
        String message = PASS_CODE_MESSAGE+ passcode;



//        MessageCreator creator = Message.creator(to,from,message);
//        creator.create();
        logger.info("send sms {}" , smsRequest + " " + message);

        //save passcode in db?
            passcodeVerificationService.savePassCode(
                    new PasscodeVerification(smsRequest.getPhoneNumber(),passcode)
            );

        }else{
            throw new IllegalStateException("pHONE NUMBER IS NOT VALUD!!" + smsRequest.getPhoneNumber());
        }

    }

    @Override
    public boolean verifyPhoneNumber(String passcode, String phoneNumber) {
      if(passcodeVerificationService.getPassCode(phoneNumber).isPresent()){
          String temp = passcodeVerificationService.getPassCode(phoneNumber).get().getPassCode();

          if(temp.equals(passcode)){

              //remove entry from passcode table
              passcodeVerificationService.deleteEntry(passcodeVerificationService.getPassCode(phoneNumber).get().getId());
          return true;
          }

          return  temp.equals(passcode);
        }
      return  false;
    }

    private boolean isPhoneValid(String phoneNumber) {
//    TODO : VALIDATE PHONE NUMBER

        return true;
    }



}
