package com.realestate.RealEstate.search;

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
    private final String longitude;
    private final String latitude;

}
