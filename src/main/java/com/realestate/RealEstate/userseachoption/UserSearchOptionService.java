package com.realestate.RealEstate.userseachoption;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.appuser.AppUserRepository;
import com.realestate.RealEstate.utils.SearchOption;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserSearchOptionService {

    private static final Logger logger = LoggerFactory.getLogger(UserSearchOptionService.class);

    private final AppUserRepository appUserRepository;

    private final UserSearchOptionRepository userSearchOptionRepository;

//    private SearchOption getSearchOption(int option){
//        if(option == 1) return SearchOption.SEARCH_BY_DISTANCE;
//        if(option == 2) return SearchOption.SEARCH_BY_CITY;
//
//        return SearchOption.SEARCH_BY_COUNTRY;
//    }

    public void addUserSearchOption(AppUser appUser, int option, double maxRange, String city, String country, LocalDateTime expiryDate){
        logger.info("Adding new userSearchOption!! for User:"+appUser.toString());
        String token  = UUID.randomUUID().toString();
        userSearchOptionRepository.save(new UserSearchOption(
                appUser,option,maxRange,city,country,expiryDate,token
        ));

    }

    public boolean updateSearchOptionForUser(long userId,int newOption){
        Optional<AppUser> userOptional = appUserRepository.findById(userId);
        if(userOptional.isEmpty()){
            logger.warn("Unable to updateSearchOptionForUser as user is not present in db!! userId:"+userId);
            return false;
        }

        AppUser appUser = userOptional.get();
        //SearchOption newSearchOption = newOption;
        logger.info("Updating user's search for user :" + userId + " , new Search Option:" + newOption);

        userSearchOptionRepository.updateUserOption(appUser,newOption);

        return true;
    }
    public UserSearchOptionResponse getUserSearchOption(long userId){
        Optional<AppUser> userOptional = appUserRepository.findById(userId);
        if(userOptional.isEmpty()){
            logger.warn("Unable to getUserSearchOption as user is not present in db!! userId:"+userId);
            return null;
        }
        AppUser appUser = userOptional.get();

        Optional<UserSearchOption> searchOptional = userSearchOptionRepository.findByAppUser(appUser);
        if(searchOptional.isEmpty()){
            logger.warn("Unable to getUserSearchOption as user's search option is not present in db!! userId:"+searchOptional.get().toString());
            return null;
        }
        UserSearchOption userSearchOption = searchOptional.get();

        return new UserSearchOptionResponse(
                userSearchOption.getSearchOption(),
                userSearchOption.getMaxRange(),
                userSearchOption.getCity(),
                userSearchOption.getCountry(),
                userSearchOption.getLicenseToken()
        );
    }



}
