package com.realestate.RealEstate.notification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Notification {
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

    private Long userId;

    private String description;

    @Column(columnDefinition = "TINYINT")
    private boolean isRead;

    @Column(nullable = false)
    private LocalDateTime createdAt;


    public Notification(Long userId, String description) {
        this.userId = userId;
        this.description = description;
        this.isRead = false;
        this.createdAt = LocalDateTime.now();

    }
}
