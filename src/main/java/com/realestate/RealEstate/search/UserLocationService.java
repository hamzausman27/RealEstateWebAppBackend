package com.realestate.RealEstate.search;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.appuser.AppUserRepository;
import com.realestate.RealEstate.appuser.AppUserRole;
import com.realestate.RealEstate.deal.DealService;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.*;

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

    boolean testAddUserLocation(){

       // appUserRepository.save(appUser);
        Double  latitude =  31.4740442832077;
       Double longitude =   74.2505517884979;


       Point point = GeometryUtil.parseLocation(latitude,longitude);
       logger.info("point" + point.toString());

       userLocationRepository.save(new UserLocation(Long.valueOf(1),"1",point));
       //userLocationRepository.addLocation("1",point);


     //  userLocationRepository.save(new UserLocation(Long.valueOf(1),"1",point));

       // Point point = new Point(latitude,longitude);

        //org.springframework.data.geo.Point point1 = new org.springframework.data.geo.Point(latitude,longitude);

        //UserLocation userLocation = new UserLocation(Long.valueOf(1),"1",point1);

        String x = "31.47404428320777";
        String y = "74.2505517884979";




        String res = "'POINT("+x+" "+y+")'";
        logger.info("point is ->" + res);

        //userLocationRepository.addLocation("1",x,y);


      // Point point = ST_PintFromText();
       //String point = "31.47404428320777 74.2505517884979";
       //String point2 = "'POINT(31.47404428320777 74.2505517884979)'";

        String point3 = "ST_PointFromText('POINT()');";


       String agentId = "1";


//        userLocationRepository.addLocation(agentId,point2,y);
//        userLocationRepository.updateCoordinate();

        //userLocationRepository.addLocation(agentId,point2);
//        int x = Integer.parseInt(latitude);
//        int y = Integer.parseInt(longitude);
//        Coordinate coordinate = new Coordinate(latitude,longitude);
//
//        Point userPoint = new Point(coordinate,precisionModel,SRID);
//
//       UserLocation userLocation = new UserLocation(
//                appUser,userPoint
//        );
//        userLocationRepository.save(userLocation);
//
//        AppUser appUser1 = new AppUser("Rare","Hamza@gmail.com","03229812559","Company","Pakistan",
//                "Punjab","lahore","gulberg lahore","123", AppUserRole.USER);
//
//        appUserRepository.save(appUser1);

//        latitude = 31.53136;
//        longitude = 74.35149;
////        int x1 = Integer.valueOf(latitude);
////        int y1 = Integer.valueOf(longitude);
//        Coordinate coordinate1 = new Coordinate(latitude,longitude);
//
//        Point userPoint1 = new Point(coordinate1,precisionModel,SRID);
//
//        UserLocation userLocation1 = new UserLocation(
//                appUser1,userPoint1
//        );
//        userLocationRepository.save(userLocation1);
//
//        AppUser appUser2 = new AppUser("canal view","Hamza@gmail.com","03229812559","Company","Pakistan",
//                "Punjab","lahore","gulberg lahore","123", AppUserRole.USER);
//
//        appUserRepository.save(appUser2);
//        latitude = 31.47397;
//        longitude = 74.24974;
////        int x2 = Integer.valueOf(latitude);
////        int y2 = Integer.valueOf(longitude);
//        Coordinate coordinate2 = new Coordinate(latitude,longitude);
//
//        Point userPoint2 = new Point(coordinate2,precisionModel,SRID);
//
//        UserLocation userLocation2 = new UserLocation(
//                appUser2,userPoint2
//        );
//        userLocationRepository.save(userLocation2);
//

        return true;
    }

}
