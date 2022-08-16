package com.realestate.RealEstate.search;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.appuser.AppUserRepository;
import com.realestate.RealEstate.appuser.AppUserRole;
import com.realestate.RealEstate.deal.DealService;
import lombok.AllArgsConstructor;
import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;
import org.geolatte.geom.crs.CoordinateReferenceSystem;
import org.geolatte.geom.jts.JTS;
import org.locationtech.jts.algorithm.Length;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class UserLocationService {

    private static final Logger logger = LoggerFactory.getLogger(UserLocationService.class);

    private final UserLocationRepository userLocationRepository;
    private final AppUserRepository appUserRepository;

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
private double calculateDistance(
        double lon1, double lat1, double lon2, double lat2) {
    GeodesicData g = Geodesic.WGS84.Inverse(lat1, lon1, lat2, lon2);
    return g.s12;  // distance in metres
}
    boolean testAddUserLocation(){

       // appUserRepository.save(appUser);
        Double  latitude =  31.50799445216071;
       Double longitude =   74.33714180511339;


     Double  latitude1 = 31.47404428320777;
      Double longitude1 = 74.2505517884979;


       logger.info("distance:" +  calculateDistance(longitude,latitude,longitude1,latitude1)/1000);


        return true;
    }

    public boolean updateUserLocation(String agentId, String longitude, String latitude) {

    if(appUserRepository.findById(Long.valueOf(agentId)).isPresent()){
        userLocationRepository.save(new UserLocation(Long.valueOf(agentId),longitude,latitude));
      logger.info("User location has been updated -> userID:" +agentId);
        return true;
    }
        logger.info("Unable to fin user with userID:" +agentId);
    return false;

    }

    public List<Double> getUserInRange(String agentId, String range){
    List<Double> usersInRange =  new ArrayList<>();
        if(appUserRepository.findById(Long.valueOf(agentId)).isPresent()){
            userLocationRepository.findByUs


        }



    }

}
