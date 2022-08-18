package com.realestate.RealEstate.userrequests;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.request.Request;
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
public class UserRequest {
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
            name = "sender_user_id"
    )
    private AppUser senderUser;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "receiver_user_id"
    )
    private AppUser receiverUser;


    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "request_id"
    )
    private Request request;


    public UserRequest(AppUser senderUser, AppUser receiverUser, Request request) {
        this.senderUser = senderUser;
        this.receiverUser = receiverUser;
        this.request = request;
    }
}
