package com.realestate.RealEstate.userrequests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SendRequest {
    private final long agentId;
    private final String title;
    private final String location;
    private final String propertySize;
    private final double propertyAmount;
    private final String tagsAdded;
    private final String description;
    private final List<Long> receiverIdList;
}
