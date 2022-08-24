package com.realestate.RealEstate.userrequests;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.appuser.AppUserRepository;
import com.realestate.RealEstate.request.Request;
import com.realestate.RealEstate.request.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
@CrossOrigin("*")
public class UserRequestController {

    private final UserRequestService userRequestService;
    private final RequestService requestService;
    private final AppUserRepository appUserRepository;


    @PostMapping("/createUserRequest")
    public List<Request> addUserRequest(@RequestBody SendRequest sendRequest){
        //create request
        LocalDateTime createdAt = LocalDateTime.now();
        Request request = new Request(sendRequest.getTitle(), sendRequest.getPropertySize(),sendRequest.getTagsAdded(),sendRequest.getPropertyAmount(),sendRequest.getLocation(),sendRequest.getDescription(),createdAt);
        for(long receiverId:sendRequest.getReceiverIdList()){
            userRequestService.addUserRequest(sendRequest.getAgentId(),receiverId,request);
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
}
