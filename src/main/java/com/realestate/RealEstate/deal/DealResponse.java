package com.realestate.RealEstate.deal;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DealResponse {
    private final boolean dealAdded;
    private final LocalDateTime createdAt;
}
