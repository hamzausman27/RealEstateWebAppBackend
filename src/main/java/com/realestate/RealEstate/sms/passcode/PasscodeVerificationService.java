package com.realestate.RealEstate.sms.passcode;

import com.realestate.RealEstate.registration.token.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PasscodeVerificationService {

    private final PasscodeVerificationRepository passcodeVerificationRepository;

    public void savePassCode(PasscodeVerification passcodeVerification){
        passcodeVerificationRepository.save(passcodeVerification);
    }
    public Optional<PasscodeVerification> getPassCode(String phoneNumber){
        return passcodeVerificationRepository.findByPhoneNumber(phoneNumber);
    }

}
