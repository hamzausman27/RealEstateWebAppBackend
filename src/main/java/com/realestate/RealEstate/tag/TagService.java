package com.realestate.RealEstate.tag;

import com.realestate.RealEstate.sms.TwilioSmsSender;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TagService {

    private static final Logger logger = LoggerFactory.getLogger(TagService.class);

    private final TagRepository tagRepository;

    public boolean saveTag(TagRequest tagRequest){
        if(null != tagRequest && !tagRequest.getTagName().isEmpty()){
            if(tagRepository.findByTagName(tagRequest.getTagName()).isPresent()){
                logger.error("Unable to save new Tag as the provided tag already exists in the database!! -> " + tagRequest.getTagName());
                return false;
            }else {
                tagRepository.save(new Tag(
                        tagRequest.getTagId(),
                        tagRequest.getTagName()
                ));
                logger.info("Tag has been added in the database! -> " + tagRequest.getTagName());
                return true;
            }


        }else {
            logger.error("Unable to save new Tag as the provided tag description is empty!!");

        }
        return false;
    }

    public boolean deleteTag(TagRequest tagRequest){

        if(null != tagRequest && !tagRequest.getTagName().isEmpty()){
            if(!tagRepository.findByTagName(tagRequest.getTagName()).isPresent()){
                logger.error("Unable to delete Tag as the provided tag does not exist in the database!! -> " + tagRequest.getTagName());
                return false;
            }else {

                tagRepository.delete(tagRepository.findByTagName(tagRequest.getTagName()).get());
                logger.info("Following Tag has been deleted from the database! -> " + tagRequest.getTagName());
                return true;
            }


        }else {
            logger.error("Unable to delete new Tag as the provided tag description is empty!!");
        }
        return false;
    }

    public List<String> getAllTags(){
        List<String> tags = new ArrayList<>();
        if(null != tagRepository && !tagRepository.findAll().isEmpty()) {
             tags = tagRepository.findAll().stream().map(Tag::getTagName).collect(Collectors.toList());
        }
        return tags;
    }



}
