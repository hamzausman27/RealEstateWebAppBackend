package com.realestate.RealEstate.tag;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping("/addTag")
    public String addTag(@RequestBody TagRequest tagRequest){
        if(tagService.saveTag(tagRequest)){
            return "Tag is added successfully";
        }
        return "Tag is not added!!";
    }

    @PostMapping("/removeTag")
    public String removeTag(@RequestBody TagRequest tagRequest){
        if(tagService.deleteTag(tagRequest)){
            return "Tag is removed successfully";
        }
        return "Tag is not removed!!";
    }

    @GetMapping("/getAllTags")
    public List<String> findAllTags(){
        return tagService.getAllTags();
    }

}
