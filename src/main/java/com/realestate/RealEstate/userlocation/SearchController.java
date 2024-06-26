package com.realestate.RealEstate.userlocation;

import com.realestate.RealEstate.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
@CrossOrigin("*")
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


    @PostMapping("/testAddUser")
    public boolean testAddUserPoint(){
        return userLocationService.testAddUserLocation();
    }

    @PostMapping("/showUserCount")
    public int showUserCount(@RequestBody LocationUpdateRequest locationUpdateRequest){
        return userLocationService.getUsersCount(locationUpdateRequest.getAgentId(),locationUpdateRequest.getOption(),locationUpdateRequest.getCity(),locationUpdateRequest.getCountry(),locationUpdateRequest.getRange());
    }

    @PostMapping("/getUserId")
    public List<Long> getReceiverList(@RequestBody LocationUpdateRequest locationUpdateRequest){
        return userLocationService.getUsersIds(locationUpdateRequest.getAgentId(),locationUpdateRequest.getOption(),locationUpdateRequest.getCity(),locationUpdateRequest.getCountry(),locationUpdateRequest.getRange());
    }

}
