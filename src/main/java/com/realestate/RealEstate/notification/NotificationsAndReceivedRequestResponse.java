package com.realestate.RealEstate.notification;

import com.realestate.RealEstate.userrequests.UserRequestInfoResponse;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class NotificationsAndReceivedRequestResponse {
    private List<UserNotificationResponse> notificationList;
    private List<UserRequestInfoResponse> allReceivedRequestList;
    private List<UserRequestInfoResponse> receivedRequestsTodayList;
}
