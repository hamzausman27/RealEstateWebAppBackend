package com.realestate.RealEstate.appuser;

import com.realestate.RealEstate.registration.token.ConfirmationToken;
import com.realestate.RealEstate.registration.token.ConfirmationTokenService;
import com.realestate.RealEstate.userseachoption.UserSearchOptionService;
import com.realestate.RealEstate.sms.SmsRequest;
import com.realestate.RealEstate.sms.SmsService;
import com.realestate.RealEstate.sms.passcode.PasscodeVerificationService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MESSAGE = "User has not been found with phone number provided!!!";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    private final PasscodeVerificationService passcodeVerificationService;

    private final UserSearchOptionService userSearchOptionService;
    private final static Logger logger = LoggerFactory.getLogger(AppUserService.class);
    private final SmsService smsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByPhoneNumber(username)
                .orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE,username)));
    }

    public Boolean signUpUser(AppUser appUser) {

        boolean userExists = checkUserExists(appUser);

        if(userExists){
           // throw new IllegalStateException("Registration fails!!!");
            return false;
        }

        String encodedPassword = passwordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);
        //saving  default search option : 3(country) and 50 km as default maxRange
        userSearchOptionService.addUserSearchOption(appUser,3,50, appUser.getCity(), appUser.getCountry());

        String token  = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        smsService.sendSms(prepareSmsRequest(appUser.getPhoneNumber()));

        return true;
    }
    public boolean loginUser(String phoneNumber, String rawPassword){
        logger.info("Trying to log in with -> phoneNumber : " + phoneNumber + "  , password : " + rawPassword);
        AppUser appUser = appUserRepository.findByPhoneNumber(phoneNumber).get();
        if(appUser != null){
            String encodedPassowrd = appUser.getPassword();
           return passwordEncoder.matches(rawPassword,encodedPassowrd);
        }
        return false;
    }

    public boolean checkUserExists(AppUser appUser){

        if(!appUserRepository.findByPhoneNumber(appUser.getPhoneNumber()).isPresent() && !appUserRepository.findByEmail(appUser.getEmail()).isPresent() ){

            return false;
        }
//        if(appUserRepository.findByEmail(appUser.getEmail()).isPresent()){
//           logger.warn("Email already exists!!!!");
//            return true;
//        }
        if(appUserRepository.findByPhoneNumber(appUser.getPhoneNumber()).isPresent()){
            logger.warn("Phone number already exists!!!!");
            return true;
        }
        return true;
    }

    public int verifyAppUser(String phoneNumber) {
        return appUserRepository.enableAppUser(phoneNumber);
    }

    private SmsRequest prepareSmsRequest(String phoneNumber){
        return new SmsRequest(phoneNumber);
    }


    public boolean resendPasscode(String phoneNumber){

        if(appUserRepository.findByPhoneNumber(phoneNumber).isPresent()){
            passcodeVerificationService.deletePrevPasscode(phoneNumber);
            smsService.sendSms(prepareSmsRequest(phoneNumber));
            logger.info("New passcode has been sent!!!");
        return true;
        }
        return false;
    }

    public boolean updateUserPassword(String phoneNumber,String rawPassword){
      return null !=appUserRepository.updateUserPassword(phoneNumber,passwordEncoder.encode(rawPassword));
    }


    public boolean checkPhoneExists(String phoneNumber){
        return appUserRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    public AppUser getAppUser(String phoneNumber){
        AppUser appUser = appUserRepository.findByPhoneNumber(phoneNumber).isPresent()?appUserRepository.findByPhoneNumber(phoneNumber).get(): null;
        return appUser;
    }


}
