package com.realestate.RealEstate.deal;

import com.realestate.RealEstate.appuser.AppUser;
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
public class Deal {
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
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser appUser;
    @Enumerated(EnumType.STRING)
    private DealStatus dealStatus;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    private String clientName;

    private String clientPhone;
    private double amount;
    private String area;
    private String clientLocation;

    private String tag;

    private String description;

    public Deal(AppUser appUser, LocalDateTime createdAt, String clientName, String clientPhone, double amount, String area, String clientLocation, String tag, String description) {
        this.appUser = appUser;
        this.dealStatus = DealStatus.NEW;
        this.createdAt = createdAt;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.amount = amount;
        this.area = area;
        this.clientLocation = clientLocation;
        this.tag = tag;
        this.description = description;
    }
}
