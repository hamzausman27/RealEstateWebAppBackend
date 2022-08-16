package com.realestate.RealEstate.search;

import com.realestate.RealEstate.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class SearchController {
    private final AppUserService appUserService;

    private final UserLocationService userLocationService;

    @PostMapping("/updateUserLocation")
    public String updateUserLocation(@RequestBody LocationUpdateRequest locationUpdateRequest){
        if(userLocationService.updateUserLocation(locationUpdateRequest.getAgentId(),locationUpdateRequest.getLongitude(),locationUpdateRequest.getLatitude())){
            return "User location is updated successfully";
        }
        return "User location is not updated!!";
    }

//    @PostMapping("/addUserPoint")
//    public boolean addUserPoint(@RequestBody LocationUpdateRequest locationUpdateRequest){
//        return userLocationService.addUserLocation(locationUpdateRequest.getAgentId(),locationUpdateRequest.getLatitude(),locationUpdateRequest.getLongitude());
//    }

    @PostMapping("/testAddUser")
    public boolean testAddUserPoint(){
        return userLocationService.testAddUserLocation();
    }


    
}
