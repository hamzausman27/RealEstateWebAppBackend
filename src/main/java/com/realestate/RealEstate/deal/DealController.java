package com.realestate.RealEstate.deal;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public boolean updateDealStatus(@RequestBody DealRequest2 dealRequest){
        return dealService.changeDealStatus(dealRequest.getDealId(),dealRequest.getDealStatus());

    }

    @PostMapping("/getAllDeals")
    public List<DealCard> getUserDeals(@RequestBody DealRequest3 dealRequest3){
                return dealService.getUserDeals(dealRequest3.getAgentId());

    }
}
