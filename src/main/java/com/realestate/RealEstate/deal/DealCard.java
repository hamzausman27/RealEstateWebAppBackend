package com.realestate.RealEstate.deal;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DealCard {
    private final String id;
    private String customerName;
    private String phoneNumber;
    private String dealAmount;
    private String area;
    private String location;
    private String tags;
    private String description;
    private String dealCreationDate;
    private String status;
}
