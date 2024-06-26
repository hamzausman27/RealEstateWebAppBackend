package com.realestate.RealEstate.request;

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
            name = "student_sequence",
            sequenceName = "sequence_name",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;

    private String title;

    private String area;

    private String tags;

    private double amount;

    private String location;

    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Request(String title, String area, String tags, double amount, String location, String description,LocalDateTime createdAt) {
        this.title = title;
        this.area = area;
        this.tags = tags;
        this.amount = amount;
        this.location = location;
        this.description = description;
        this.createdAt = createdAt;

    }
}
