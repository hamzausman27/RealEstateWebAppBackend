package com.realestate.RealEstate.deal;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.appuser.AppUserRepository;
import com.realestate.RealEstate.contact.ContactRequest;
import com.realestate.RealEstate.contact.ContactService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DealService {
    private static final Logger logger = LoggerFactory.getLogger(DealService.class);

    private final DealRepository dealRepository;
    private final AppUserRepository appUserRepository;

    private final ContactService contactService;

    DealResponse addDeal(DealRequest dealRequest){

        long agentId = Long.valueOf(dealRequest.getAgentID());
        Optional<AppUser> appUser =   appUserRepository.findById(agentId);
        if(null != dealRequest && appUser.isPresent()){

            LocalDateTime createdAt = LocalDateTime.now();
            Deal newDeal = new Deal(
                    appUser.get(),
                    createdAt,
                    dealRequest.getClientName(),
                    dealRequest.getClientPhoneNumber(),
                    dealRequest.getAmount(),
                    dealRequest.getArea(),
                    dealRequest.getLocation(),
                    dealRequest.getTag(),
                    dealRequest.getDescription()
            );

            dealRepository.save(newDeal);
            logger.info("Deal has been added in db!!");


            contactService.saveContact(new ContactRequest(
                    dealRequest.getAgentID(),
                    dealRequest.getClientName(),
                    dealRequest.getClientPhoneNumber(),
                    dealRequest.getLocation(),
                    dealRequest.getTag()
            ));

            return new DealResponse(true,createdAt, newDeal.getId());

        }
        logger.error("Deal request is:" + dealRequest.getAgentID() + "  ,"+ dealRequest.getClientName() + "  ,"+ dealRequest.getClientPhoneNumber() + "  ,"+ dealRequest.getArea() + "  ,"+ dealRequest.getAmount() + "  ,"+ dealRequest.getLocation() + "  ,"+ dealRequest.getTag() + "  ," + dealRequest.getDescription() + "  ,");
        logger.error("Unable to add new deal!!!");
        return new DealResponse(false,null,0L);
    }

    boolean removeDeal(Long dealId){
        if(dealRepository.findById(dealId).isPresent()){
            dealRepository.delete(dealRepository.findById(dealId).get());
            logger.info("Deal has been deleted in db : deal id = " + dealId);
        return true;
        }
        logger.error("Unable to delete deal: Could not find requestedId! : deal id = " + dealId);
        return false;
    }

    boolean updateDeal(DealRequest4 dealRequest4){
        logger.info("Update deal request is :");
        if(dealRepository.findById(dealRequest4.getDealId()).isPresent()){

            dealRepository.editDeal(dealRequest4.getDealId(),dealRequest4.getClientName(),dealRequest4.getClientPhoneNumber(),dealRequest4.getArea(),dealRequest4.getAmount(),dealRequest4.getLocation(),dealRequest4.getDescription(),dealRequest4.getTag());
            logger.info("Deal has been edited!!!");
            return true;
        }
        logger.warn("Unable to edit deal!!!");
        return false;
    }

    boolean changeDealStatus(Long dealId,String dealStatus){

        if(dealId != null && dealStatus != null){
            if(dealRepository.findById(dealId).isPresent()){

                if(dealStatus.equals("DONE") ||dealStatus.equals("NEW") || dealStatus.equals("INPROGRESS") || dealStatus.equals("LOST") ){
                    DealStatus dealStatus1 = DealStatus.valueOf(dealStatus);
                    logger.info("Deal Status: deal status = " + dealStatus1);
                    dealRepository.updateDealStatus(dealId,dealStatus1);
                    logger.info("Deal status has been udpated in db : deal id = " + dealId + " status = " + dealStatus1);
                    return true;
                }
                logger.error("Unable to update deal: requested deal status is invalid! : deal status = " + dealStatus);

            }
            logger.error("Unable to update deal: Could not find requestedId! : deal id = " + dealId);
            return false;
        }
        logger.error("Unable to update deal: Could not find requestedId! : deal id = " + dealId);
        return false;
    }

    private String getDealStatus(DealStatus dealStatus){
        if(dealStatus == DealStatus.NEW) return "NEW";
        if(dealStatus == DealStatus.INPROGRESS) return "INPROGRESS";
        if(dealStatus == DealStatus.DONE) return "DONE";

        return "LOST";

    }

    public List<DealCard> getUserDeals(String agentId) {
        Optional<AppUser> appUser = appUserRepository.findById(Long.valueOf(agentId));
        if(appUser.isPresent()){
        AppUser appUser1 = appUser.get();
        List<Deal> userDeals = dealRepository.findAllByAppUser(appUser1);
        List<DealCard> dealCards = new ArrayList<>();
        for(Deal deal : userDeals)
            dealCards.add(new DealCard(
                    deal.getId().toString(),
                    deal.getClientName(),
                    deal.getClientPhone(),
                    String.valueOf(deal.getAmount()),
                    deal.getArea(),
                    deal.getClientLocation(),
                    deal.getTag(),
                    deal.getDescription(),
                    deal.getCreatedAt().toString(),
                    getDealStatus(deal.getDealStatus())
            ));
        return dealCards;
        }
        logger.warn("No user deals are found against user id:"+ agentId);
        return null;
    }
}
