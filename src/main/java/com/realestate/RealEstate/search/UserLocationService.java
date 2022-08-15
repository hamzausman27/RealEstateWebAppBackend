package com.realestate.RealEstate.search;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.appuser.AppUserRepository;
import com.realestate.RealEstate.deal.DealService;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserLocationService {

    private static final Logger logger = LoggerFactory.getLogger(UserLocationService.class);

    private final UserLocationRepository userLocationRepository;
    private final AppUserRepository appUserRepository;

    boolean addUserLocation(String agentId,String latitude, String longitude){
        AppUser appUser = appUserRepository.findById(Long.valueOf(agentId)).get();

          int x = Integer.valueOf(latitude);
         int y = Integer.valueOf(longitude);
        Coordinate coordinate = new Coordinate(x,y);
        PrecisionModel precisionModel = new PrecisionModel(PrecisionModel.FIXED);
        int SRID =0;
        Point userPoint = new Point(coordinate,precisionModel,SRID);

        userLocationRepository.save(new UserLocation(
                appUser,userPoint
        ));

        return true;
    }


}
