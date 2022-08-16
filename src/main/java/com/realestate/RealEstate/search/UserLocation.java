package com.realestate.RealEstate.search;

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

    private Long userId;

    private String longitude;

    private String latitude;

    public UserLocation(Long userId, String longitude, String latitude) {
        this.userId = userId;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
