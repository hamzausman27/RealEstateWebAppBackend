package com.realestate.RealEstate.contact;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.appuser.AppUserRepository;
import com.realestate.RealEstate.tag.TagService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ContactService {

    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);

    private final ContactRepository contactRepository;

    private final AppUserRepository appUserRepository;

    public boolean saveContact(ContactRequest contactRequest){

        if(null != contactRequest) {
            Optional<AppUser> appUserOptional = appUserRepository.findById(Long.valueOf(contactRequest.getAgentId()));

            if(appUserOptional.isEmpty()){
                logger.error("Unable to save new contact as the provided agent id does not exists in the database!! -> " + contactRequest.getAgentId());
                return false;
            } else {
                contactRepository.save(
                        new Contact(
                                appUserOptional.get(),
                                contactRequest.getName(),
                                contactRequest.getPhoneNumber(),
                                contactRequest.getAddress(),
                                contactRequest.getTag()
                        ));
                logger.info("Contact has been added in the database! -> " + contactRequest.toString());
                return true;
            }
        }
        logger.error("Unable to save new contact as the provided request is empty!! -> " + contactRequest.toString());

        return false;
    }


    public boolean deleteContact(ContactRequest2 contactRequest){

        Optional<Contact> userContactOptional = contactRepository.findById(contactRequest.getContactId());
        if(userContactOptional.isPresent()){
            Contact contact = userContactOptional.get();
            logger.info("Going to delete contact -> contact:"+contact.toString());
            contactRepository.delete(contact);
            logger.info("Contact deleted!");
            return true;
        }
        logger.warn("Unable to delete contact as it doest not exits in db -> contact_id:"+contactRequest.getContactId());
        return false;

    }

    public List<ContactCard> getAllUserContacts(String agentId){
        List<ContactCard> contactCardList = new ArrayList<>();
        Optional<AppUser> appUserOptional = appUserRepository.findById(Long.valueOf(agentId));
        if(appUserOptional.isPresent()){
            for(Contact contact:contactRepository.findAllByAppUser(appUserOptional.get())){
                contactCardList.add(new ContactCard(
                        String.valueOf(contact.getContactId()),
                        contact.getName(),
                        contact.getPhoneNumber(),
                        contact.getAddress(),
                        contact.getTag()
                        ));
            }
        }
        return contactCardList;
    }

    public List<Contact>getContactsByTags(ContactRequest contactRequest){
        return null;
    }

}
