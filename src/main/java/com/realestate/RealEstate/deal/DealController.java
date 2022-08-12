package com.realestate.RealEstate.deal;

import com.realestate.RealEstate.contact.ContactRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
@CrossOrigin("*")
public class DealController {

    private final DealService dealService;

    @PostMapping("/addDeal")
    public DealResponse addDeal(@RequestBody DealRequest dealRequest){

        return dealService.addDeal(dealRequest);
    }

    @PostMapping("/removeDeal")
    public Boolean removeDeal(@RequestBody DealRequest2 dealRequest){

        return dealService.removeDeal(dealRequest.getDealId());

    }

    @PostMapping("/updateDealStatus")
    public String updateDealStatus(@RequestBody DealRequest2 dealRequest){
        if(dealService.changeDealStatus(dealRequest.getDealId(),dealRequest.getDealStatus())){
            return "Deal status has been updated successfully";
        }
        return "Deal status has not been updated!!";
    }

    @PostMapping("/getAllDeals")
    public List<Deal> getUserDeals(@RequestBody DealRequest3 dealRequest3){
                return dealService.getUserDeals(dealRequest3.getAgentId());

    }
}
