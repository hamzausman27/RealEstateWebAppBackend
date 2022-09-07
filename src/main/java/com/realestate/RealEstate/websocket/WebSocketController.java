package com.realestate.RealEstate.websocket;

import com.realestate.RealEstate.deal.DealService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin("*")
public class WebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @MessageMapping("/message") //send to  /app/message
    @SendTo("/chatroom/public")
    private Message receivePublicMessage(@Payload Message message){

        logger.info("Received Public message is : receiver:" + message.getReceiverName()+ " sender:" + message.getSenderName()+" content:" +message.getMessage());
        return message;
    }

    @MessageMapping("/private-message")
    private Message receivePrivateMessage(@Payload Message message){

        logger.info("Received Private message is : receiver:" + message.getReceiverName()+ " sender:" + message.getSenderName()+" content:" +message.getMessage());
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message); // /user/David/private

        return message;
    }

}
