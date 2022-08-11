package com.realestate.RealEstate.registration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String fullName;
    private final String email;
    private final String password;
    private final String phone;
    private final String companyName;
    private final String country;
    private final String state;
    private final String city;
    private final String area;
}
