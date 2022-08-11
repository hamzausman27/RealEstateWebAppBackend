package com.realestate.RealEstate.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class InputRequest {
    private final Long requestId;
    private final String agentName;
    private final String agentPhoneNumber;
    private final String location;
    private final String propertySize;
    private final String propertyAmount;
    private final String tagsAdded;

}
