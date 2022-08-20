package com.realestate.RealEstate.userlocation;

import com.realestate.RealEstate.appuser.AppUser;
import com.sun.istack.NotNull;
import lombok.*;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class UserLocation {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "sequence_name",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;

    @OneToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser appUser;

    private double longitude;

    private double latitude;

    public UserLocation(AppUser appUser, double longitude, double latitude) {
        this.appUser = appUser;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
