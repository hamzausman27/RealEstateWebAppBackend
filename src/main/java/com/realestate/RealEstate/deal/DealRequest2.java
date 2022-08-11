package com.realestate.RealEstate.deal;


import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DealRequest2 {
    Long dealId;
    String dealStatus;

    public DealRequest2(Long dealId) {
        this.dealId = dealId;
    }
}
