package com.realestate.RealEstate.sms.passcode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PasscodeVerification {

    @Id
    @SequenceGenerator(
            name = "passcode_verification_sequence",
            sequenceName = "passcode_verification_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "passcode_verification_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String passCode;

    public PasscodeVerification(String phoneNumber, String passCode) {
        this.phoneNumber = phoneNumber;
        this.passCode = passCode;
    }
}
