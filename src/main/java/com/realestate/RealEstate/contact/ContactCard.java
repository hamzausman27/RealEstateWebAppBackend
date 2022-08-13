package com.realestate.RealEstate.contact;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ContactCard {
    private final String id;
    private final String customerName;
    private final String phoneNumber;
    private final String location;
    private final String tags;
}
