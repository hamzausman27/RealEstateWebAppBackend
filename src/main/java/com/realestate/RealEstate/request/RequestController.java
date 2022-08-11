package com.realestate.RealEstate.request;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class RequestController {

    private final RequestService requestService;
    //  @PostMapping("/addDeal")
    //    public String addDeal(@RequestBody DealRequest dealRequest){
    //        if(dealService.addDeal(dealRequest)){
    //            return "Deal is added successfully";
    //        }
    //        return "Deal is not added!!";
    //    }

    @PostMapping("/addRequest")
    public String addRequest(@RequestBody InputRequest inputRequest){
        if(requestService.addRequest(inputRequest)){
            return "Request is added successfully!";
        }
        return "Request is not added!!";
    }
    @PostMapping("/deleteRequest")
    public String deleteRequest(@RequestBody InputRequest inputRequest){
        if(requestService.deleteRequest(inputRequest)){
            return "Request is added successfully!";
        }
        return "Request is not added!!";
    }

}
