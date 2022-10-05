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
    public boolean addContact(@RequestBody ContactRequest contactRequest){
       return contactService.saveContact(contactRequest);
    }

    @PostMapping("/removeContact")
    public boolean removeContact(@RequestBody ContactRequest2 contactRequest){
        return contactService.deleteContact(contactRequest);
    }

    @PostMapping("/removeMultipleContacts")
    public void removeMultipleContacts(@RequestBody ContactRequest2 contactRequest){
        contactService.deleteContactList(contactRequest);
    }

//    @PostMapping("/updateContact")
//    public String updateContact(@RequestBody ContactRequest contactRequest){
//        if(contactService.updateContact(contactRequest)){
//            return "Contact is updated successfully";
//        }
//        return "Contact is not updated!!";
//    }

    @PostMapping("/getAllUserContacts")
    public List<ContactCard> findAllContacts(@RequestBody ContactRequest contactRequest){
        return contactService.getAllUserContacts(contactRequest.getAgentId());
    }
}
