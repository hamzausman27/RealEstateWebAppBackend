package com.realestate.RealEstate.userrequests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RequestObject {
    private final long userId;
    private final long userRequestId;
    private final String newPhone;
    private final String newAddress;
    private final String newName;
    private final String oldPassword;

}
