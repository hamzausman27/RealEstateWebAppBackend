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


    @PostMapping("/updateUserLocation")
    public String updateUserLocation(@RequestBody LocationUpdateRequest locationUpdateRequest){
        if(appUserService.updateUserLocation(locationUpdateRequest.getPhoneNumber(),locationUpdateRequest.getLongitude(),locationUpdateRequest.getLatitude())){
            return "User location is updated successfully";
        }
        return "User location is not updated!!";
    }
    
}
