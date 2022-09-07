package com.realestate.RealEstate.notification;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserNotificationResponse {
    private final String notificationDescription;
    private final LocalDateTime notificationDate;
    private final Boolean notificationRead;

}
