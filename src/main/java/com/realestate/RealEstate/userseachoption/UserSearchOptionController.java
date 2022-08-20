package com.realestate.RealEstate.userseachoption;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
@CrossOrigin("*")
public class UserSearchOptionController {

    private final UserSearchOptionService userSearchOptionService;

    @PostMapping("/getUserSearchOption")
    public UserSearchOptionResponse getUserSearchOption(@RequestBody UserSearchOptionRequest userSearchOptionRequest){
     return  userSearchOptionService.getUserSearchOption(userSearchOptionRequest.getUserId());
    }



}
