package com.realestate.RealEstate.admin;

import com.realestate.RealEstate.appuser.AppUser;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AdminResponseObject {
    private final List<UserInfoForAdmin> allUsers;
    private final List<AppUser> activeUsers;
    private final List<AppUser> pendingUsers;
    private final List<AppUser> blockedUsers;
}
