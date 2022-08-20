package com.realestate.RealEstate.userseachoption;

import com.realestate.RealEstate.appuser.AppUser;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class UserSearchOption {

    @Id
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private Long id;

    @OneToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser appUser;

   // @Enumerated(EnumType.STRING)
    private int searchOption;

    private Double maxRange;

    private String city;

    private String country;

    public UserSearchOption(AppUser appUser, int searchOption, Double maxRange, String city, String country) {
        this.appUser = appUser;
        this.searchOption = searchOption;
        this.maxRange = maxRange;
        this.city = city;
        this.country = country;
    }
}
