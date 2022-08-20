package com.realestate.RealEstate.userlocation;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LocationUpdateRequest {

    private final String agentId;
    private final double longitude;
    private final double latitude;
    private final int option;
    private final int range;
    private final String city;
    private final String country;

}
