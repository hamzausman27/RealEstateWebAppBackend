package com.realestate.RealEstate.userrequests;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.appuser.AppUserRepository;
import com.realestate.RealEstate.request.Request;
import com.realestate.RealEstate.request.RequestRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserRequestService {
    private static final Logger logger = LoggerFactory.getLogger(UserRequestService.class);

    private final AppUserRepository appUserRepository;
    private final RequestRepository requestRepository;
    private final UserRequestRepository userRequestRepository;

    public boolean addUserRequest(Long senderId, Long receiverId, Long requestId){
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
        Optional<Request> requestOptional = requestRepository.findById(requestId);
        if(requestOptional.isEmpty()){
            logger.warn("UserRequest add failed!! Request id is not found in db ->receiverId:"+requestId);
            return false;
        }
        userRequestRepository.save(new UserRequest(
                senderOptional.get(),receiverOptional.get(),requestOptional.get()
        ));
        logger.info("UserRequest has been added in db!!");

        return true;
    }

}
