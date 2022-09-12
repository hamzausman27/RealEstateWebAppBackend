package com.realestate.RealEstate.registration;

import com.realestate.RealEstate.appuser.LogInResponse;
import com.realestate.RealEstate.userinfo.UserInfo;
import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.appuser.AppUserService;
import com.realestate.RealEstate.registration.token.ConfirmationTokenService;
import com.realestate.RealEstate.sms.passcode.PasscodeVerificationService;
import com.realestate.RealEstate.userseachoption.UserSearchOptionService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
@CrossOrigin("*")
public class RegistrationController {

    private final static Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private RegistrationService registrationService;
    private AppUserService appUserService;
    private PasscodeVerificationService passcodeVerificationService;

    private ConfirmationTokenService confirmationTokenService;

    private UserSearchOptionService userSearchOptionService;

    @PostMapping("/registration")
    public Boolean register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

//    @GetMapping(path = "confirm")
//    public String confirm(@RequestParam("token") String token) {
//        return registrationService.confirmToken(token);
//    }

    @PostMapping("/verify")
    public Boolean sendSms(@RequestBody VerificationRequest verificationRequest){
        if(registrationService.verifyPhoneNumber(verificationRequest)){
            logger.info("User is verified!!");
            return true;
        }
        logger.warn("User is not verified!!");
        return false;
    }
    @PostMapping("/login")
    public LogInResponse login(@RequestBody LoginRequest loginRequest){

        return appUserService.loginUser(loginRequest.getPhone(),loginRequest.getPassword());

    }

    @PostMapping("/resendPasscode")
    public Boolean resendPassCode(@RequestBody VerificationRequest resendPassCodeRequest){
        return appUserService.resendPasscode(resendPassCodeRequest.getPhone());

    }

    @PostMapping("/resetPassword")
    public Boolean resetPassword(@RequestBody ResetPasswordRequest resetPassword){
             return appUserService.updateUserPassword(resetPassword.getPhone(), resetPassword.getPassword());
    }

    @PostMapping("/verifyPhone")
    public Boolean verifyPhone(@RequestBody ResetPasswordRequest resetPassword){
        return appUserService.checkPhoneExists(resetPassword.getPhone());
    }

    @PostMapping("/verifyPasscode")
    public Boolean verifyPasscode(@RequestBody VerificationRequest verificationRequest){
        return passcodeVerificationService.verifyPasscode(verificationRequest.getPhone(),verificationRequest.getPasscode());
    }


    @PostMapping("/getUserInfo")
    public UserInfo getUserInfo(@RequestBody VerificationRequest verificationRequest){
        AppUser appUser = appUserService.getAppUser(verificationRequest.getPhone());
        String license = userSearchOptionService.getLicenseNumber(appUser);
        return new UserInfo(
                appUser.getId(),
                appUser.getFullName(),
                appUser.getEmail(),
                appUser.getPhoneNumber(),
                appUser.getCompanyName(),
                appUser.getCountry(),
                appUser.getState(),
                appUser.getCity(),
                appUser.getArea(),
                license
        );
    }


}
