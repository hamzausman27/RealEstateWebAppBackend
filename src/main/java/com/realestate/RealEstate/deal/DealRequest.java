package com.realestate.RealEstate.deal;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DealRequest {
    private final String agentID;
    private final String clientName;
    private final String clientPhoneNumber;
    // will contain both area unit and value  20 KANAL
    private final String area;
    private final String tag;
    private final double amount;
    private final String location;
    private final String description;
}
