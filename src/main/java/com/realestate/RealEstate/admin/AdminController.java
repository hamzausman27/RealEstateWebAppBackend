package com.realestate.RealEstate.admin;

import com.realestate.RealEstate.appuser.AppUser;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
@CrossOrigin("*")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final AdminUserService adminUserService;

    @GetMapping("/getAdminDashboardData")
    public AdminResponseObject getUsersDataForAdmin(){
        List<UserInfoForAdmin> allUserList = adminUserService.showAllUsers();
        List<AppUser> activeUserList = adminUserService.showActiveUsers();
        List<AppUser> pendingUserList = adminUserService.showPendingUsers();
        List<AppUser> blockedUserList = adminUserService.showBlockedUsers();
        logger.info("Admin info: AllUser:"+allUserList.size()+ ", ActiveUsers:"+ activeUserList.size() + ", PendingUsers:" +pendingUserList.size() +", BlockedUsers:"+blockedUserList.size()+".");
        return new AdminResponseObject(allUserList,activeUserList,pendingUserList,blockedUserList);
    }

    @PostMapping("/deleteUserData")

    public boolean removeUserData(@RequestBody UserInfoForAdmin userInfoForAdmin){

        return adminUserService.removeUserData(userInfoForAdmin.getId());

    }
    @PostMapping("/blockOrUnblockUser")
    public boolean blockOrUnblockUser(@RequestBody UserInfoForAdmin userInfoForAdmin){

        if(userInfoForAdmin.getStatus().equals("Active")){
            return adminUserService.blockUser(userInfoForAdmin.getId());
        }else if(userInfoForAdmin.getStatus().equals("Blocked")){
            return adminUserService.unBlockUser(userInfoForAdmin.getId());
        }

        return false;
    }

    @PostMapping("/unBlockUser")
    public boolean unBlockUser(@RequestBody UserInfoForAdmin userInfoForAdmin){

        return adminUserService.unBlockUser(userInfoForAdmin.getId());

    }

    @PostMapping("/approveUser")
    public boolean approveUser(@RequestBody UserSearchInfo userSearchInfo){

        return adminUserService.approvePendingAccount(userSearchInfo.getId(), userSearchInfo.getSearchOption(), userSearchInfo.getMaxRange(),userSearchInfo.getExpiryDate());

    }


}
