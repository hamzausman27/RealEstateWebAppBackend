package com.realestate.RealEstate.deal;

import com.realestate.RealEstate.contact.ContactRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public String removeDeal(@RequestBody DealRequest2 dealRequest){
        if(dealService.removeDeal(dealRequest.getDealId())){
            return "Deal is deleted successfully";
        }
        return "Deal is not deleted!!";
    }

    @PostMapping("/updateDealStatus")
    public String updateDealStatus(@RequestBody DealRequest2 dealRequest){
        if(dealService.changeDealStatus(dealRequest.getDealId(),dealRequest.getDealStatus())){
            return "Deal status has been updated successfully";
        }
        return "Deal status has not been updated!!";
    }

}
