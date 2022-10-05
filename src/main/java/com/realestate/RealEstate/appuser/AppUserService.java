package com.realestate.RealEstate.appuser;

import com.realestate.RealEstate.sms.SmsRequest;
import com.realestate.RealEstate.sms.VeevoSmsService;
import com.realestate.RealEstate.sms.passcode.PasscodeVerificationService;
import com.realestate.RealEstate.userseachoption.UserSearchOptionResponse;
import com.realestate.RealEstate.userseachoption.UserSearchOptionService;
import com.realestate.RealEstate.utils.AppConstants;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MESSAGE = "User has not been found with phone number provided!!!";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final PasscodeVerificationService passcodeVerificationService;

    private final static Logger logger = LoggerFactory.getLogger(AppUserService.class);
    private final VeevoSmsService veevoSmsService;

    private final UserSearchOptionService userSearchOptionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByPhoneNumber(username)
                .orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE,username)));
    }

    public SignUpResponse signUpUser(AppUser appUser) {

        boolean userExists = checkUserExists(appUser);

        if(userExists){
           // throw new IllegalStateException("Registration fails!!!");
            return new SignUpResponse(false,"This phone number is already registered!");
        }

        String encodedPassword = passwordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        if(appUser.getPhoneNumber().equals(AppConstants.ADMIN_PHONE_1) || appUser.getPhoneNumber().equals(AppConstants.ADMIN_PHONE_2) ){
            appUser.setAppUserRole(AppUserRole.ADMIN);
            appUser.setLocked(false);
            appUser.setVerified(true);
        }

        appUserRepository.save(appUser);

        return new SignUpResponse(true,"User added!");
    }
    public LogInResponse loginUser(String phoneNumber, String rawPassword){
        LogInResponse logInResponse = null;
        logger.info("Trying to log in with -> phoneNumber : " + phoneNumber + "  , password : " + rawPassword);
        Optional<AppUser> appUserOptional = appUserRepository.findByPhoneNumber(phoneNumber);
        if(!appUserOptional.isPresent()){
            logger.warn("Log In failed!! User with phone number:"+phoneNumber +" does not exit in database!!");
            return new LogInResponse(false,false,false,false);
        }
        AppUser appUser = appUserOptional.get();
        String encodedPassword = appUser.getPassword();
        boolean checkLogin = passwordEncoder.matches(rawPassword, encodedPassword);
        boolean isAdmin = appUser.getAppUserRole().equals(AppUserRole.ADMIN);
        Boolean appUserVerified = appUser.getVerified();
        if(!isAdmin && appUserVerified){
            logger.info("Checking if user package is expired or not!");
            UserSearchOptionResponse userSearchOption = userSearchOptionService.getUserSearchOption(appUser.getId());
            if(userSearchOption.getExpiryDate().isBefore(LocalDate.now())){
                logger.warn("User package is expired!! Locking it!");
                appUser.setLocked(true);
                appUserRepository.blockAppUser(appUser.getId());
            }

        }
        Boolean appUserLocked = appUser.getLocked();
        logger.info("User Role-> Admin :" + isAdmin);


        logger.info("User Role-> AccountLocked :" + appUserLocked);
        if(!checkLogin){
            logger.warn("Log in failed!! Incorrect credentials!");

        }else{
            logger.info("Logged in successfully! Role->isAdmin:");
        }

        return new LogInResponse(checkLogin,isAdmin,appUserLocked,appUserVerified);

    }


//    private void addDummyData(){
//        AppUser appUser = new AppUser("Hamza Usman","hamza@gmail.com","+923229882558","sage","PK","PB","Lahore","Johar town","123456789",AppUserRole.USER);
//
//        AppUser appUser1 = new AppUser("Hamza Rehman","hamza@gmail.com","+923229882557","sage","PK","PB","Lahore","Johar town","123456789",AppUserRole.USER);
//
//        AppUser appUser2 = new AppUser("Haris Khan","hamza@gmail.com","+923229882556","sage","PK","PB","Lahore","Johar town","123456789",AppUserRole.USER);
//
//        signUpUser(appUser);
//        signUpUser(appUser1);
//        signUpUser(appUser2);
//
//    }

    public boolean checkUserExists(AppUser appUser){

        if(!appUserRepository.findByPhoneNumber(appUser.getPhoneNumber()).isPresent()){

            return false;
        }
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
            veevoSmsService.sendMessage(phoneNumber);
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


    public void udpateNotificationSound(long userId) {

        Optional<AppUser> appUserOptional = appUserRepository.findById(userId);
        if(appUserOptional.isPresent()){
            Boolean notificationSound = appUserOptional.get().getNotificationSound();
            if(notificationSound){
                logger.info("Muting notification sound for user:"+userId);
                appUserRepository.muteNotificationSound(userId);
            }else{
                logger.info("Unmuting notification sound for user:"+userId);
                appUserRepository.unMuteNotificationSound(userId);
            }

            logger.info("Notification sound changed successfully for user:"+userId);

        }else{
            logger.warn("muteNotificationSound FAILED !!! User id is invalid -> userId:"+userId);
        }

    }

    public EditUserInfoResponse editUserInfo(Long userId,String newPhone,String newName,String newAddress){
        Optional<AppUser> appUserOptional = appUserRepository.findById(userId);
        if(appUserOptional.isPresent()){
            logger.info("Checking if new phone already exits!!");
            Optional<AppUser> checkUserOptional = appUserRepository.findByPhoneNumber(newPhone);
            if(checkUserOptional.isPresent() && !Objects.equals(checkUserOptional.get().getId(), userId)){
                logger.warn("New phone is already in use!!!");
                return new EditUserInfoResponse(false,"Update Failed as given phone number is already in use! entered phone:"+ newPhone);
            }else{
                logger.info("Going to edit user info!!!");
                appUserRepository.editUserInfo(userId,newPhone,newName,newAddress);
                return new EditUserInfoResponse(true,"User Info has been updated!");
            }

        }
        logger.warn("Invalid user id!!!!");
        return new EditUserInfoResponse(false,"Incorrect UserID given");
    }

    public boolean checkOldPassword(Long userId,String oldPassword){
        Optional<AppUser> appUserOptional = appUserRepository.findById(userId);
        if(appUserOptional.isPresent()){
            AppUser appUser = appUserOptional.get();
            String encodedPassword = appUser.getPassword();
            boolean checkLogin = passwordEncoder.matches(oldPassword, encodedPassword);
            if(checkLogin){
                logger.info("CorrectPassword");
            }else{
                logger.warn("Incorrect password");
            }
            return checkLogin;

        }

        logger.warn("User id is invalid! userId:"+ userId);
        return false;
    }

}
