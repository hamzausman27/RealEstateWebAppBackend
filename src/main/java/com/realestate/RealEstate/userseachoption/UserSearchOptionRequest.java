package com.realestate.RealEstate.userseachoption;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserSearchOptionRequest {
    private final long userId;
    private final long phone;
}
