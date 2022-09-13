package com.realestate.RealEstate.userseachoption;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserSearchOptionResponse {
    private final int searchOption;
    private final double maxRange;
    private final String ciy;
    private final String country;
    private final String token;
    private final LocalDate expiryDate;

}
