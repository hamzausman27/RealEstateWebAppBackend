package com.realestate.RealEstate.request;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RequestService {

    private static final Logger logger = LoggerFactory.getLogger(RequestService.class);

    private final RequestRepository requestRepository;

    Request createRequest(String title,String area,String tags,double amount,String location,String description) {

        LocalDateTime createdAt = LocalDateTime.now();
        Request request = new Request(title,area,tags,amount,location,description,createdAt);
            requestRepository.save(request);
            logger.info("Request has been created!!");

        return request;
    }

//    public boolean deleteRequest(InputRequest inputRequest) {
//        if (requestRepository.findById(inputRequest.getRequestId()).isPresent()) {
//            requestRepository.delete(requestRepository.findById(inputRequest.getRequestId()).get());
//            logger.info("Request has been deleted!!");
//            return true;
//        }
//        logger.error("Unable to delete request!");
//        logger.error("Request id is not present in database!! Request Id:" + inputRequest.getRequestId());
//        return false;
//    }
//
//    public List<Request> getRequestsSentToday() {
////TODO
//        return null;
//    }
//
//    public List<Request> getRequestReceivedToday() {
////TODO
//        return null;
//    }
//
//    public List<Request> getTotalRequestsSent() {
////TODO
//        return null;
//    }
//
//    public List<Request> getTotalRequestsSentToday() {
//
//        //TODO
//        return null;
//    }

}
