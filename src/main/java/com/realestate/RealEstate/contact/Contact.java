package com.realestate.RealEstate.contact;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.tag.Tag;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Contact {
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
    private Long contactId;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser appUser;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String address;

    private LocalDateTime dateOfCreation;
    @Column(nullable = false)
    private String tag;


    public Contact(AppUser appUser,String name, String phoneNumber, String address, String tag) {
        this.appUser = appUser;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfCreation = LocalDateTime.now();
        this.tag = tag;
    }
}
