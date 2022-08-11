package com.realestate.RealEstate.contact;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ContactRequest {

    private final String name;
    private final String phoneNumber;
    private final String address;
    private final String tag;

}
