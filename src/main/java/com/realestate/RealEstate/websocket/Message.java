package com.realestate.RealEstate.websocket;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Message {

    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private Status status;
}
