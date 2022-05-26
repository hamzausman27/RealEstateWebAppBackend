package com.realestate.RealEstate.registration;

import com.realestate.RealEstate.sms.SmsRequest;
import com.realestate.RealEstate.sms.SmsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;
    private final SmsService smsService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @PostMapping("/sms")
    public void sendSms(@RequestBody SmsRequest smsRequest){
        smsService.sendSms(smsRequest);
    }

}
