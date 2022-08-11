package com.realestate.RealEstate.registration;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.appuser.AppUserRole;
import com.realestate.RealEstate.appuser.AppUserService;
import com.realestate.RealEstate.registration.token.ConfirmationToken;
import com.realestate.RealEstate.registration.token.ConfirmationTokenService;
import com.realestate.RealEstate.sms.SmsSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;
    private final SmsSender smsSender;

    public Boolean register(RegistrationRequest request) {


        return appUserService.signUpUser(
            new AppUser(request.getFullName(),request.getEmail(),request.getPhone(),request.getCompanyName(),request.getCountry(),
                    request.getState(),request.getCity(),request.getArea(),request.getPassword(),AppUserRole.USER)
        );



    }

    public boolean verifyPhoneNumber(VerificationRequest verificationRequest){
        if(smsSender.verifyPhoneNumber(verificationRequest.getPasscode(),verificationRequest.getPhone())){
            appUserService.verifyAppUser(verificationRequest.getPhone());
            return true;
        }
        return false;
    }

}
