package com.realestate.RealEstate.appuser;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class EditUserInfoResponse {
    private final boolean updated;
    private final String updatedDesc;
}
