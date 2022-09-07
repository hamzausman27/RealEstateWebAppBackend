package com.realestate.RealEstate.admin;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.appuser.AppUserRepository;
import com.realestate.RealEstate.appuser.AppUserRole;
import com.realestate.RealEstate.userseachoption.UserSearchOptionService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
            if(!appUser.getAppUserRole().equals(AppUserRole.ADMIN)){
                if(!appUser.getVerified()){
                    status = "Pending";
                }else if(appUser.getVerified() && appUser.getLocked()){
                    status = "Blocked";
                }else if(appUser.getVerified() && !appUser.getLocked()){
                    status = "Active";
                }
                 allUsers.add(new UserInfoForAdmin(appUser.getId(),appUser.getFullName(),appUser.getPhoneNumber(),appUser.getArea(),appUser.getCity(),appUser.getState(),appUser.getCountry(),status));
            }
        }
        return allUsers;
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

    public boolean approvePendingAccount(Long agentId, int searchOption, int maxRange, LocalDateTime expirationDate){
        Optional<AppUser> appUserOptional = appUserRepository.findById(agentId);
        if(appUserOptional.isEmpty()){
            logger.warn("Invalid user id!! userId:"+agentId);
            return false;
        }
        AppUser appUser = appUserOptional.get();
        //LocalDateTime expiryDate =;

        appUser.setLocked(false);
        logger.info("Adding user SearchOption");
        userSearchOptionService.addUserSearchOption(appUser,searchOption,maxRange, appUser.getCity(), appUser.getCountry(),expirationDate);


        return false;
    }


}