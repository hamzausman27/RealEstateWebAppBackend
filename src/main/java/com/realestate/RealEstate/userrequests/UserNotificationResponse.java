package com.realestate.RealEstate.userrequests;

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
    private final Long id;

    private final String title;

    private final String area;

    private final String tags;

    private final double amount;

    private final String location;

    private final String description;

    private final LocalDateTime createdAt;

    private final String senderName;
}
