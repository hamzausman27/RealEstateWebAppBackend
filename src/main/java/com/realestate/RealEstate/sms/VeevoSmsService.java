package com.realestate.RealEstate.sms;

import com.realestate.RealEstate.sms.passcode.PasscodeVerification;
import com.realestate.RealEstate.sms.passcode.PasscodeVerificationService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static com.realestate.RealEstate.utils.AppConstants.PASS_CODE_MESSAGE;
import static com.realestate.RealEstate.utils.AppConstants.VEEVOTECH_HASH_KEY;

@Service
@AllArgsConstructor
public class VeevoSmsService {
    private final PasscodeVerificationService passcodeVerificationService;

    public void sendMessage(String phoneNumber) {
        String passcode = RandomStringUtils.randomNumeric(6);
        String message = PASS_CODE_MESSAGE +passcode;

            //save passcode in db?
            passcodeVerificationService.savePassCode(
                    new PasscodeVerification(phoneNumber,passcode)
            );

        String request2 = "https://api.veevotech.com/sendsms?hash="+VEEVOTECH_HASH_KEY+"&receivernum="+phoneNumber+"&receivernetwork=default&screen_name=&sender_address=&textmessage="+message+"&sendernum=8583";
       try {
           URL url = new URL(request2);
           URLConnection conn = url.openConnection();
           InputStream input = conn.getInputStream();

       } catch (MalformedURLException e) {
           throw new RuntimeException(e);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }

    }

    }

