package com.realestate.RealEstate.userrequests;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.appuser.AppUserRepository;
import com.realestate.RealEstate.request.Request;
import com.realestate.RealEstate.request.RequestRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserRequestService {
    private static final Logger logger = LoggerFactory.getLogger(UserRequestService.class);

    private final AppUserRepository appUserRepository;
    private final RequestRepository requestRepository;
    private final UserRequestRepository userRequestRepository;

    public boolean addUserRequest(Long senderId, Long receiverId, Request request){
        Optional<AppUser> senderOptional = appUserRepository.findById(senderId);
        if(senderOptional.isEmpty()){
            logger.warn("UserRequest add failed!!1Sender id is not found in db ->senderId:"+senderId);
            return false;
        }
        Optional<AppUser> receiverOptional = appUserRepository.findById(receiverId);
        if(receiverOptional.isEmpty()){
            logger.warn("UserRequest add failed!! Receiver id is not found in db ->receiverId:"+receiverId);
            return false;
        }
        logger.info("Adding request with id:"+request.getId());
        requestRepository.save(request);

        logger.info("Adding requests in user_requests table for sender:"+senderId +"  and receiver id:"+receiverId +" and request id:"+ request.getId());
//        Optional<Request> requestOptional = requestRepository.findById(requestId);
//        if(requestOptional.isEmpty()){
//            logger.warn("UserRequest add failed!! Request id is not found in db ->receiverId:"+requestId);
//            return false;
//        }
        userRequestRepository.save(new UserRequest(
                senderOptional.get(),receiverOptional.get(),request
        ));
        logger.info("UserRequest has been added in db!!");

        return true;
    }

    public List<Request> fetchSentRequests(long senderId) {

        List<Request> sentRequestList = new ArrayList<>();

        List<Long> sentRequestIds = fetchSentRequestsIdList(senderId);

        logger.info("Total sent request count for sender:"+ senderId + " is :" + sentRequestIds.size());
        for(Long requestId: sentRequestIds) {
            Optional<Request> requestOptional = requestRepository.findById(requestId);
            requestOptional.ifPresent(sentRequestList::add);
        }
        return sentRequestList;

    }
    private List<Long> fetchSentRequestsIdList(Long senderId) {
        logger.info("fetching sent request ids for sender:"+ senderId);
        return userRequestRepository.fetchSentRequestIds(senderId);
    }

    public List<UserRequestInfoResponse> fetchReceivedRequests(long receivedId) {
        List<UserRequestInfoResponse> receivedRequestList = new ArrayList<>();
        List<Long> receivedRequestsIds = fetchReceivedRequestsIdList(receivedId);

        logger.info("Total sent request count for receiver:"+ receivedId + " is :" + receivedRequestsIds.size());
        for(Long requestId: receivedRequestsIds) {
            Optional<Request> requestOptional = requestRepository.findById(requestId);
            if(requestOptional.isPresent()){
                Request request = requestOptional.get();
                Optional<UserRequest> userRequestOptional = userRequestRepository.searchByRequest(request);

                if(userRequestOptional.isPresent()){
                }
                AppUser sender = userRequestOptional.get().getSenderUser();
                receivedRequestList.add(new UserRequestInfoResponse(sender.getFullName(),sender.getPhoneNumber(),request.getId(),
                        request.getTitle(),request.getArea(),request.getTags(),request.getAmount(),request.getLocation(),
                        request.getDescription(),request.getCreatedAt()));
            }
        }
        return receivedRequestList;
    }

    private List<Long> fetchReceivedRequestsIdList(Long receiverId) {
        logger.info("fetching sent request ids for sender:"+ receiverId);
        return userRequestRepository.fetchReceivedRequestIds(receiverId);
    }
}
