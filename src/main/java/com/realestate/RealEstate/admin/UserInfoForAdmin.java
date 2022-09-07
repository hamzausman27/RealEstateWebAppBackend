package com.realestate.RealEstate.admin;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserInfoForAdmin {
    private final Long id;
    private final String fullName;
    private final String phoneNumber;
    private final String location;
    private final String city;
    private final String state;
    private final String country;
    private final String status;
}
