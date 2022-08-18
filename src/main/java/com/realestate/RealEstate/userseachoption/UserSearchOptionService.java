package com.realestate.RealEstate.userseachoption;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.appuser.AppUserRepository;
import com.realestate.RealEstate.utils.SearchOption;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserSearchOptionService {

    private static final Logger logger = LoggerFactory.getLogger(UserSearchOptionService.class);

    private final AppUserRepository appUserRepository;

    private final UserSearchOptionRepository userSearchOptionRepository;

    private SearchOption getSearchOption(int option){
        if(option == 1) return SearchOption.SEARCH_BY_DISTANCE;
        if(option == 2) return SearchOption.SEARCH_BY_CITY;

        return SearchOption.SEARCH_BY_COUNTRY;
    }

    public void addUserSearchOption(AppUser appUser,int option,double maxRange,String city,String country){
        logger.info("Adding new userSearchOption!! for User:"+appUser.toString());
        userSearchOptionRepository.save(new UserSearchOption(
                appUser,getSearchOption(option),maxRange,city,country
        ));

    }

    public boolean updateSearchOptionForUser(long userId,int newOption){
        Optional<AppUser> userOptional = appUserRepository.findById(userId);
        if(userOptional.isEmpty()){
            logger.warn("Unable to updateSearchOptionForUser as user is not present in db!! userId:"+userId);
            return false;
        }

        AppUser appUser = userOptional.get();
        SearchOption newSearchOption = getSearchOption(newOption);
        logger.info("Updating user's search for user :" + userId + " , new Search Option:" + newSearchOption.toString());

        userSearchOptionRepository.updateUserOption(appUser,newSearchOption);

        return true;
    }



}
