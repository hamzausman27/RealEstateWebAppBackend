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
    public List<DealCard> addDeal(@RequestBody DealRequest dealRequest){

                dealService.addDeal(dealRequest);
            return dealService.getUserDeals(dealRequest.getAgentID());
    }

    @PostMapping("/removeDeal")
    public List<DealCard> removeDeal(@RequestBody DealRequest2 dealRequest){

       dealService.removeDeal(dealRequest.getDealId());

       return dealService.getUserDeals(dealRequest.getAgentId());
    }
    @PostMapping("/editDeal")
    public boolean editDeal(@RequestBody DealRequest4 dealRequest) {

        return dealService.updateDeal(dealRequest);
    }

    @PostMapping("/updateDealStatus")
    public String updateDealStatus(@RequestBody DealRequest2 dealRequest){
        if(dealService.changeDealStatus(dealRequest.getDealId(),dealRequest.getDealStatus())){
            return "Deal status has been updated successfully";
        }
        return "Deal status has not been updated!!";
    }

    @PostMapping("/getAllDeals")
    public List<DealCard> getUserDeals(@RequestBody DealRequest3 dealRequest3){
                return dealService.getUserDeals(dealRequest3.getAgentId());

    }
}
