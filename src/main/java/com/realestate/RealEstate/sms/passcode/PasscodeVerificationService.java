package com.realestate.RealEstate.sms.passcode;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.registration.token.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PasscodeVerificationService {

    private final PasscodeVerificationRepository passcodeVerificationRepository;

    public void savePassCode(PasscodeVerification passcodeVerification){
        passcodeVerificationRepository.save(passcodeVerification);
    }

    @Query("Select a.passcode from passcode_verification a WHERE a.phone_number = ?1")
    public Optional<PasscodeVerification> getPassCode(String phoneNumber){
        return passcodeVerificationRepository.findByPhoneNumber(phoneNumber);
    }

    public void deleteEntry(Long id){
        passcodeVerificationRepository.deleteById(id);
    }

    public void deletePrevPasscode(String phoneNumber){
         passcodeVerificationRepository.deleteByPhoneNumber(phoneNumber);
    }

    public boolean verifyPasscode(String phoneNumber,String userPasscode){
        Optional<PasscodeVerification> passcodeVerification = passcodeVerificationRepository.findByPhoneNumber(phoneNumber);
        if(passcodeVerification.isPresent()){
            return userPasscode.equals(passcodeVerification.get().getPassCode());
        }
        return false;
    }


}
