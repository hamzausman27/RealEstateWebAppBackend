package com.realestate.RealEstate.userrequests;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.appuser.AppUserRepository;
import com.realestate.RealEstate.notification.NotificationService;
import com.realestate.RealEstate.request.Request;
import com.realestate.RealEstate.request.RequestService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
@CrossOrigin("*")
public class UserRequestController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    private final UserRequestService userRequestService;

    private final RequestService requestService;
    private final AppUserRepository appUserRepository;
    private final NotificationService notificationService;


    private static final Logger logger = LoggerFactory.getLogger(UserRequestController.class);

    @PostMapping("/createUserRequest")
    public List<Request> addUserRequest(@RequestBody SendRequest sendRequest){
        //create request
        LocalDateTime createdAt = LocalDateTime.now();
        AppUser senderUser = appUserRepository.findById(sendRequest.getAgentId()).get();
        Request request = new Request(sendRequest.getTitle(), sendRequest.getPropertySize(),sendRequest.getTagsAdded(),sendRequest.getPropertyAmount(),sendRequest.getLocation(),sendRequest.getDescription(),createdAt);
        for(long receiverId:sendRequest.getReceiverIdList()){
            userRequestService.addUserRequest(sendRequest.getAgentId(),receiverId,request);
            String notificationDesc = "You have received a request from "+senderUser.getFullName()+".";
            notificationService.addUserNotification(receiverId,notificationDesc);
            AppUser receiverUser = appUserRepository.findById(receiverId).get();
            UserNotificationResponse userNotificationResponse = new UserNotificationResponse(request.getId(), request.getTitle(), request.getArea(), request.getTags(), request.getAmount(), request.getLocation(), request.getDescription(), request.getCreatedAt(),senderUser.getFullName());
            logger.info("Received Private message is : receiver:" + receiverUser.getPhoneNumber()+ " sender:" + senderUser.getPhoneNumber());

            simpMessagingTemplate.convertAndSendToUser(receiverUser.getPhoneNumber(),"/private",userNotificationResponse); // /user/David/private

        }
        return userRequestService.fetchSentRequests(sendRequest.getAgentId());
    }

    @PostMapping("/getSentRequests")
    public List<Request> getUserSentRequests(@RequestBody SendRequest sendRequest){
        return userRequestService.fetchSentRequests(sendRequest.getAgentId());
    }
    @PostMapping("/getReceivedRequests")
    public List<UserRequestInfoResponse> getUserReceivedRequests(@RequestBody SendRequest sendRequest){
        return userRequestService.fetchReceivedRequests(sendRequest.getAgentId());
    }
    @PostMapping("/deleteReceivedRequests")
    public void deleteReceivedRequests(@RequestBody RequestObject requestObject){
         userRequestService.deleteUserReceivedRequest(requestObject.getUserId(),requestObject.getUserRequestId());
    }
    @PostMapping("/accpetReceivedRequests")
    public void acceptReceivedRequests(@RequestBody RequestObject requestObject){
         userRequestService.updateUserReceivedRequestAsAccepted(requestObject.getUserId(),requestObject.getUserRequestId());
    }

}
