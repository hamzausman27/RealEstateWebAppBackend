package com.realestate.RealEstate.notification;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserNotificationResponse {
    private final String notificationDescription;
    private final LocalDateTime notificationDate;
    private final Boolean notificationRead;

}
