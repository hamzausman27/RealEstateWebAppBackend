package com.realestate.RealEstate.appuser;

import com.realestate.RealEstate.registration.token.ConfirmationToken;
import com.realestate.RealEstate.registration.token.ConfirmationTokenService;
import com.realestate.RealEstate.sms.VeevoSmsService;
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
import java.util.Objects;
import java.util.Optional;
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
    private final VeevoSmsService veevoSmsService;

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

        if(appUser.getPhoneNumber().equals("+923229882559")){
            appUser.setAppUserRole(AppUserRole.ADMIN);
            appUser.setLocked(false);
            appUser.setVerified(true);
        }

        appUserRepository.save(appUser);
        //saving  default search option : 3(country) and 50 km as default maxRange
       // userSearchOptionService.addUserSearchOption(appUser,3,50, appUser.getCity(), appUser.getCountry());

        String token  = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

       // confirmationTokenService.saveConfirmationToken(confirmationToken);

       // smsService.sendSms(prepareSmsRequest(appUser.getPhoneNumber()));

        return true;
    }
    public LogInResponse loginUser(String phoneNumber, String rawPassword){
        LogInResponse logInResponse = null;
        logger.info("Trying to log in with -> phoneNumber : " + phoneNumber + "  , password : " + rawPassword);
        Optional<AppUser> appUserOptional = appUserRepository.findByPhoneNumber(phoneNumber);
        if(appUserOptional.isEmpty()){
            logger.warn("Log In failed!! User with phone number:"+phoneNumber +" does not exit in database!!");
            return new LogInResponse(false,false,false,false);
        }
        AppUser appUser = appUserOptional.get();
        String encodedPassword = appUser.getPassword();
        boolean checkLogin = passwordEncoder.matches(rawPassword, encodedPassword);
        boolean isAdmin = appUser.getAppUserRole().equals(AppUserRole.ADMIN);
        Boolean appUserLocked = appUser.getLocked();
        Boolean appUserVerified = appUser.getVerified();
        logger.info("User Role-> Admin :" + isAdmin);
        if(isAdmin){
            addDummyData();
        }

        logger.info("User Role-> AccountLocked :" + appUserLocked);
        if(!checkLogin){
            logger.warn("Log in failed!! Incorrect credentials!");

        }else{
            logger.info("Logged in successfully! Role->isAdmin:");
        }

        return new LogInResponse(checkLogin,isAdmin,appUserLocked,appUserVerified);

    }


    private void addDummyData(){
        AppUser appUser = new AppUser("Hamza Usman","hamza@gmail.com","+923229882558","sage","PK","PB","Lahore","Johar town","123456789",AppUserRole.USER);

        AppUser appUser1 = new AppUser("Hamza Rehman","hamza@gmail.com","+923229882557","sage","PK","PB","Lahore","Johar town","123456789",AppUserRole.USER);

        AppUser appUser2 = new AppUser("Haris Khan","hamza@gmail.com","+923229882556","sage","PK","PB","Lahore","Johar town","123456789",AppUserRole.USER);

        signUpUser(appUser);
        signUpUser(appUser1);
        signUpUser(appUser2);

    }

    public boolean checkUserExists(AppUser appUser){

        if(!appUserRepository.findByPhoneNumber(appUser.getPhoneNumber()).isPresent()){

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
            veevoSmsService.sendMessage(phoneNumber);
          //  smsService.sendSms(prepareSmsRequest(phoneNumber));
            logger.info("New passcode has been sent!!!");
//            VeevoSmsService veevoSmsService = new VeevoSmsService();
//            veevoSmsService.sendMessage(phoneNumber);
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
