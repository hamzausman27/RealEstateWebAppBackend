package com.realestate.RealEstate.contact;

import com.realestate.RealEstate.tag.TagService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ContactService {

    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);

    private final ContactRepository contactRepository;

    public boolean saveContact(ContactRequest contactRequest){

        if(null != contactRequest) {
            if (contactRepository.findByPhoneNumber(contactRequest.getPhoneNumber()).isPresent()) {
                logger.error("Unable to save new contact as the provided phone number already exists in the database!! -> " + contactRequest.getPhoneNumber());

                return false;
            } else {

                contactRepository.save(
                        new Contact(
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

    public boolean updateContact(ContactRequest contactRequest){

        if(deleteContact(contactRequest)){
            if(saveContact(contactRequest)){
                logger.info("Contact has been updated in the database! -> " + contactRequest.toString());
                return true;
            }else{

                logger.error("Unable to insert updated contact!! -> " + contactRequest.toString());
                return false;
            }
        }
        logger.error("Unable to update contact!! -> " + contactRequest.toString());

        return false;
    }

    public boolean deleteContact(ContactRequest contactRequest){
        if(null != contactRequest) {
            if (contactRepository.findByPhoneNumber(contactRequest.getPhoneNumber()).isEmpty()) {
                logger.error("Unable to save new contact as the provided phone number already exists in the database!! -> " + contactRequest.getPhoneNumber());

                return false;
            } else {

                contactRepository.delete(
                        contactRepository.findByPhoneNumber(contactRequest.getPhoneNumber()).get());
                logger.info("Contact has been deleted from the database! -> " + contactRequest.toString());
                return true;
            }
        }
        logger.error("Unable to delete requested contact as the provided request is empty!! -> " + contactRequest.toString());
        return false;
    }

    public List<Contact> getAllContacts(){
        return contactRepository.findAll();
    }

    public List<Contact>getContactsByTags(ContactRequest contactRequest){
        return null;
    }

}
