package com.realestate.RealEstate.admin;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.appuser.AppUserRepository;
import com.realestate.RealEstate.appuser.AppUserRole;
import com.realestate.RealEstate.userseachoption.UserSearchOptionResponse;
import com.realestate.RealEstate.userseachoption.UserSearchOptionService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminUserService {
    private final static Logger logger = LoggerFactory.getLogger(AdminUserService.class);
    private final AppUserRepository appUserRepository;
    private final UserSearchOptionService userSearchOptionService;

    // show all users
    public List<UserInfoForAdmin> showAllUsers(){
        List<UserInfoForAdmin> allUsers = new ArrayList<>();
        String status = null;
        for(AppUser appUser: appUserRepository.findAll()){
            if(!appUser.getAppUserRole().equals(AppUserRole.ADMIN) && appUser.getVerified()){
                 if(appUser.getVerified() && appUser.getLocked()){
                    status = "Blocked";
                }else if(appUser.getVerified() && !appUser.getLocked()){
                    status = "Active";
                }
                UserSearchOptionResponse userSearchOption = userSearchOptionService.getUserSearchOption(appUser.getId());

                allUsers.add(new UserInfoForAdmin(appUser.getId(),appUser.getFullName(),appUser.getPhoneNumber(),appUser.getArea(),appUser.getCity(),appUser.getState(),appUser.getCountry(),status,userSearchOption.getExpiryDate(),getSearchOptionString(userSearchOption.getSearchOption(),userSearchOption.getMaxRange())));
            }
        }
        return allUsers;
    }

    private String getSearchOptionString(int option,int maxDistance){
        String res= "";
        if(option == 1) {
            res = "Range("+maxDistance+" km)";
        }else if(option == 2) {
            res = "City,Range("+maxDistance+" km)";
        }else if(option == 3) {
            res = "Country,City,Range("+maxDistance+" km)";
        }
        return res;
    }

    // show pendingRequest
    public List<AppUser> showPendingUsers(){
        List<AppUser> pendingUsers = new ArrayList<>();
        for(AppUser appUser: appUserRepository.findAll()){
            if(!appUser.getAppUserRole().equals(AppUserRole.ADMIN) && !appUser.getVerified()){
                pendingUsers.add(appUser);
            }
        }
        return pendingUsers;
    }
    // show active users
    public List<AppUser> showActiveUsers(){
        List<AppUser> activeUsers = new ArrayList<>();
        for(AppUser appUser: appUserRepository.findAll()){
            if(!appUser.getAppUserRole().equals(AppUserRole.ADMIN) && !appUser.getLocked() && appUser.getVerified()){
                activeUsers.add(appUser);
            }
        }
        return activeUsers;
    }

    // show blocked users
    public List<AppUser> showBlockedUsers(){
        List<AppUser> blockedUsers = new ArrayList<>();
        for(AppUser appUser: appUserRepository.findAll()){
            if(!appUser.getAppUserRole().equals(AppUserRole.ADMIN) && appUser.getLocked() && appUser.getVerified()){
                blockedUsers.add(appUser);
            }
        }
        return blockedUsers;
    }

    public boolean approvePendingAccount(Long agentId, int searchOption, int maxRange, LocalDate expirationDate){
        Optional<AppUser> appUserOptional = appUserRepository.findById(agentId);
        if(!appUserOptional.isPresent()){
            logger.warn("Invalid user id!! userId:"+agentId);
            return false;
        }
        AppUser appUser = appUserOptional.get();

        logger.info("Adding user SearchOption");
        userSearchOptionService.addUserSearchOption(appUser,searchOption,maxRange, appUser.getCity(), appUser.getCountry(),expirationDate);
        logger.info("Verifying user!!");
        appUserRepository.verifyAppUser(agentId);

        return true;
    }


    public boolean removeUserData(Long id) {
        Optional<AppUser> appUserOptional = appUserRepository.findById(id);
        if(!appUserOptional.isPresent()){
            logger.warn("Unable to delete user as it does not exist in db! id:"+id);
            return false;
        }
        appUserRepository.deleteById(id);
        logger.info("User with id :"+id+" has been deleted!!");
        return true;

    }

    public boolean blockUser(Long id) {
        Optional<AppUser> appUserOptional = appUserRepository.findById(id);
        if(!appUserOptional.isPresent()){
            logger.warn("Unable to block user as it does not exist in db! id:"+id);
            return false;
        }
        appUserRepository.blockAppUser(id);
        logger.info("User with id:"+id+" has been blocked!");
        return true;
    }

    public boolean unBlockUser(Long id) {
        Optional<AppUser> appUserOptional = appUserRepository.findById(id);
        if(!appUserOptional.isPresent()){
            logger.warn("Unable to unblock user as it does not exist in db! id:"+id);
            return false;
        }
        appUserRepository.unBlockAppUser(id);
        logger.info("User with id:"+id+" has been unblocked!");
        return true;
    }
}
