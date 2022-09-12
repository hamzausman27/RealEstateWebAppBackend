package com.realestate.RealEstate.notification;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class NotificationRequest {
    private final Long id;
    private final String phone;
}
