package com.realestate.RealEstate.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@ToString
public class UserSearchInfo {

    private final Long id;

    private final LocalDate expiryDate;

    private final int maxRange;

    private final int searchOption;
}
