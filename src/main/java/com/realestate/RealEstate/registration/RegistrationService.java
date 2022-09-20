package com.realestate.RealEstate.registration;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.appuser.AppUserRole;
import com.realestate.RealEstate.appuser.AppUserService;
import com.realestate.RealEstate.appuser.SignUpResponse;
import com.realestate.RealEstate.sms.SmsSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final SmsSender smsSender;

    public SignUpResponse register(RegistrationRequest request) {


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
