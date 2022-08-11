package com.realestate.RealEstate.request;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.deal.Deal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Request {
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
            name = "deal_id"
    )
    private Deal deal;
    @Column(nullable = false)
    private String agentName;

    @Column(nullable = false)
    private String agentPhoneNumber;
    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String propertySize;

    @Column(nullable = false)
    private Double propertyAmount;

    @Column(nullable = false)
    private String tagsAdded;

    @Column(nullable = false)
    private LocalDateTime requestCreatedAt;

    public Request(String agentName, String agentPhoneNumber, String location, String propertySize, Double propertyAmount, String tagsAdded) {
        this.agentName = agentName;
        this.agentPhoneNumber = agentPhoneNumber;
        this.location = location;
        this.propertySize = propertySize;
        this.propertyAmount = propertyAmount;
        this.tagsAdded = tagsAdded;
        this.requestCreatedAt = LocalDateTime.now();
    }
}
