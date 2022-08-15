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

    private String agentId;
//    @ManyToOne
//    @JoinColumn(
//            nullable = false,
//            name = "app_user_id"
//    )
//    private AppUser appUser;

    @NotNull
    @Column(columnDefinition = "POINT")
    private Point coordinate;
//
//    @Column(columnDefinition = "TEXT")
//    private String latitude;
//
//    @Column(columnDefinition = "TEXT")
//    private String longitude;

//
//    public UserLocation(AppUser appUser, Point coordinate) {
////        this.appUser = appUser;
//        this.coordinate = coordinate;
//    }


//    public UserLocation(Point coordinate) {
//        this.coordinate = coordinate;
//    }
//
//    public UserLocation(String agentId, Point coordinate) {
//        this.agentId = agentId;
//        this.coordinate = coordinate;
//    }

    public UserLocation(Long id, String agentId, Point coordinate) {
        this.id = id;
        this.agentId = agentId;
        this.coordinate = coordinate;
    }

//
//    public UserLocation(Long id, String agentId, String latitude, String longitude) {
//        this.id = id;
//        this.agentId = agentId;
//        this.latitude = latitude;
//        this.longitude = longitude;
//    }
}
