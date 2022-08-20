package com.realestate.RealEstate.userseachoption;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserSearchOptionResponse {
    private final int searchOption;
    private final double maxRange;
    private final String ciy;
    private final String country;
}
