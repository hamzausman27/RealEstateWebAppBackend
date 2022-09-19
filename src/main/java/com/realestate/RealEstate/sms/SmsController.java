package com.realestate.RealEstate.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2/sms")
public class SmsController {

    private final SmsService smsService;

    private final VeevoSmsService veevoSmsService;
    @Autowired
    public SmsController(SmsService smsService, VeevoSmsService veevoSmsService) {
        this.smsService = smsService;
        this.veevoSmsService = veevoSmsService;
    }

    @PostMapping
    public void sendSms(@RequestBody SmsRequest smsRequest){

        smsService.sendSms(smsRequest);
    }

    @PostMapping("/veve")
    public void sendSms2() {

            veevoSmsService.sendMessage("+923229882559");

    }
}
