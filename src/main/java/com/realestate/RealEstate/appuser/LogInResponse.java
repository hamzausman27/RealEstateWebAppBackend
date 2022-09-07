package com.realestate.RealEstate.appuser;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LogInResponse {
    private final boolean loggedInSuccess;
    private final boolean adminAccount;
    private final boolean accountLocked;
    private final boolean accountVerified;
}
