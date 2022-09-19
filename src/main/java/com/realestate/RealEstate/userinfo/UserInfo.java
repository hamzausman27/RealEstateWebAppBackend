package com.realestate.RealEstate.userinfo;

import com.realestate.RealEstate.notification.UserNotificationResponse;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserInfo {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String companyName;
    private String country;
    private String state;
    private String city;
    private String area;
    private String licenseNumber;
    private int notificationsCount;
    private List<UserNotificationResponse> notificationList;
    private boolean notificationSound;
}
