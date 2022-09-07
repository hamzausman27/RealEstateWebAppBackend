package com.realestate.RealEstate.userinfo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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
}
