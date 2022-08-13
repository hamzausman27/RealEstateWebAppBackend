package com.realestate.RealEstate.contact;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
@CrossOrigin("*")
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/addContact")
    public String addContact(@RequestBody ContactRequest contactRequest){
        if(contactService.saveContact(contactRequest)){
            return "Contact is added successfully";
        }
        return "Contact is not added!!";
    }

    @PostMapping("/removeContact")
    public String removeContact(@RequestBody ContactRequest contactRequest){
        if(contactService.deleteContact(contactRequest)){
            return "Contact is removed successfully";
        }
        return "Contact is not removed!!";
    }

    @PostMapping("/updateContact")
    public String updateContact(@RequestBody ContactRequest contactRequest){
        if(contactService.updateContact(contactRequest)){
            return "Contact is updated successfully";
        }
        return "Contact is not updated!!";
    }

    @PostMapping("/getAllUserContacts")
    public List<ContactCard> findAllContacts(@RequestBody ContactRequest contactRequest){
        return contactService.getAllUserContacts(contactRequest.getAgentId());
    }
}
