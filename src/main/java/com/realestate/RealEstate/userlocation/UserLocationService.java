package com.realestate.RealEstate.userlocation;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.appuser.AppUserRepository;
import com.realestate.RealEstate.userseachoption.UserSearchOption;
import com.realestate.RealEstate.userseachoption.UserSearchOptionRepository;
import lombok.AllArgsConstructor;
import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@AllArgsConstructor
public class UserLocationService {

    private static final Logger logger = LoggerFactory.getLogger(UserLocationService.class);

    private final UserLocationRepository userLocationRepository;
    private final AppUserRepository appUserRepository;

    private final UserSearchOptionRepository userSearchOptionRepository;

//    boolean addUserLocation(String agentId,String latitude, String longitude){
//        AppUser appUser = appUserRepository.findById(Long.valueOf(agentId)).get();
//
//          int x = Integer.valueOf(latitude);
//         int y = Integer.valueOf(longitude);
//        Coordinate coordinate = new Coordinate(x,y);
//        PrecisionModel precisionModel = new PrecisionModel(PrecisionModel.FIXED);
//        int SRID =0;
//        Point userPoint = new Point(coordinate,precisionModel,SRID);
//
//        userLocationRepository.save(new UserLocation(
//                appUser,userPoint
//        ));
//
//        return true;
//    }
private double calculateDistance(double lon1, double lat1, double lon2, double lat2) {
    GeodesicData g = Geodesic.WGS84.Inverse(lat1, lon1, lat2, lon2);
    return g.s12/1000;  // distance in kilometers
}

private boolean checkUserInRange(UserLocation userLocation,UserLocation otherUser,double range){

    return calculateDistance(userLocation.getLongitude(),userLocation.getLatitude(),
            otherUser.getLongitude(),otherUser.getLatitude()) <= range;

}

    public boolean testAddUserLocation(){

       // appUserRepository.save(appUser);
        Double  latitude =  31.50799445216071;
       Double longitude =   74.33714180511339;


     Double  latitude1 = 31.47404428320777;
      Double longitude1 = 74.2505517884979;


       logger.info("distance:" +  calculateDistance(longitude,latitude,longitude1,latitude1)/1000);


        return true;
    }

    public boolean updateUserLocation(String agentId, double longitude, double latitude) {
       Optional<AppUser> appUserOptional = appUserRepository.findById(Long.valueOf(agentId));
    if(appUserOptional.isPresent()){
        Optional<UserLocation> userLocationOptional = userLocationRepository.findByAppUser(appUserOptional.get());
        if(userLocationOptional.isPresent()){
            logger.info("Updating location of existing user -> userID:" +agentId + ", latitude:"+ latitude + " , longitude:"+longitude);
            userLocationRepository.updateExistingUserLocation(userLocationOptional.get().getId(),latitude,longitude);

        }else{
            logger.info("User location doesnot exist therefore inserting location! -> userId" + agentId);
            userLocationRepository.save(new UserLocation(appUserOptional.get(),longitude,latitude));
        }


      logger.info("User location has been updated -> userID:" +agentId);
        return true;
    }
        logger.info("Unable to find user with userID:" +agentId);
    return false;

    }

    public List<Long> getUserInRange(long agentId, double range){
    List<Long> usersInRange =  new ArrayList<>();
    Optional<AppUser> appUserOptional = appUserRepository.findById(agentId);
    if(appUserOptional.isPresent()){
        Optional<UserLocation> userLocationOptional =  userLocationRepository.findByAppUser(appUserOptional.get());
        if(userLocationOptional.isPresent() && userLocationOptional.get().getLongitude()!=0 && userLocationOptional.get().getLatitude()!=0){
            logger.info("User location is present -> Getting user in range!!");
            UserLocation userLocation = userLocationOptional.get();
            List<UserLocation> userLocationList = userLocationRepository.findAll();
            for(UserLocation userLocation1:userLocationList){
                if(userLocation1.getAppUser().getId()!=agentId){
                    if(checkUserInRange(userLocation,userLocation1,range)){
                        usersInRange.add(userLocation1.getAppUser().getId());
                    }
                }
            }
            logger.info("Total user in range for user:"+agentId +" and distance range:"+range+" are:" +usersInRange.size());
        }
        logger.warn("User location is not valid!! -> ");
        }

    return usersInRange;

    }

    public int getUsersCount(String agentId,int option, String city,String country, int range) {
    int count = 0;
    if(option == 3){
       // UserSearchOption searchOption = userSearchOptionRepository.findByAppUser()
       List<AppUser> userSearchOptionList =  appUserRepository.findAll();
       logger.info("total users in db:" , userSearchOptionList.size());
        Stream<AppUser> filteredUsersInCountry = userSearchOptionList.stream().filter(val -> Objects.equals(val.getCountry(), country));
       // logger.info("total users in db:" , filteredUsersInCountry.collect(Collectors.toList()).size());
        count= filteredUsersInCountry.collect(Collectors.toList()).size();
        logger.info("Number of users for country:" + country +" , option:" + option + " are : "+count );

        return count;
    }else if(option == 2){
            // UserSearchOption searchOption = userSearchOptionRepository.findByAppUser()
            List<AppUser> userSearchOptionList =  appUserRepository.findAll();
        logger.info("total users in db:" , userSearchOptionList.size());
        Stream<AppUser> filteredUsersInCity = userSearchOptionList.stream().filter(val -> Objects.equals(val.getCity(), city));
        count= filteredUsersInCity.collect(Collectors.toList()).size();
            logger.info("Number of users for city:" + city +" , option:" + option + " are : "+count );
            return count;
        }
        else if(option == 1) {
        count =  getUserInRange(Long.parseLong(agentId),range).size();
        logger.info("Number of users in range:" + range +" , option:" + option + " are : "+count );

        return count;
    }
    return 0;
    }
}
