package com.realestate.RealEstate.contact;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ContactRequest2 {


    private final Long contactId;
    private final String agentId;
    private final List<Long> contactList;
}
