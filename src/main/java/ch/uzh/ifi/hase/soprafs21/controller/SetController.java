package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Set;
import ch.uzh.ifi.hase.soprafs21.rest.dto.SetGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.SetPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.SetService;
import ch.uzh.ifi.hase.soprafs21.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Card Controller
 * This class is responsible for handling all REST request that are related to the user.
 * The controller will receive the request and delegate the execution to the UserService and finally return the result.
 */

@RestController
public class SetController {

    @Autowired
    private SetService setService;

    @Autowired
    private SettingsService settingsService;

// All Get Mappings:

    // Get all public sets
    @GetMapping("/sets")
    @ResponseStatus(HttpStatus.OK)
    public List<SetGetDTO> getPublicSets(){
        // fetch all sets in the internal representation
        List<Set> publicSets = setService.getPublicSets();
        List<SetGetDTO> setGetDTOs = new ArrayList<>();

        // convert each user to the API representation
        for (Set publicSet : publicSets){
            setGetDTOs.add(DTOMapper.INSTANCE.convertEntityToSetGetDTO(publicSet));
        }

        // return list of all public sets
        return setGetDTOs;
    }

    // Get flashcardset with setId
    @GetMapping("/sets/{setId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SetGetDTO getSetBySetId(@PathVariable("setId") Long setId) {
        // Get specific set by setId
        Set set = setService.getSetBySetId(setId);

        return DTOMapper.INSTANCE.convertEntityToSetGetDTO(set);
    }


// All Put Mappings:

    // Update a specific set of cards
    @PutMapping ("/sets")
    @ResponseStatus(HttpStatus.OK)
    public SetGetDTO getSetByID(@RequestBody SetPostDTO setPostDTO) {

        Set setInput = DTOMapper.INSTANCE.convertSetPostDTOtoEntity(setPostDTO);

        Set updatedSet = setService.updateSet(setInput);

        //update cardOrder -> default order & lastCardId to first
        settingsService.updateCardOrder(updatedSet);

        //update starredCards
        return DTOMapper.INSTANCE.convertEntityToSetGetDTO(updatedSet);
    }

    // Update members of a specific set of cards
    @PutMapping("/sets/{userId}/{setId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SetGetDTO updateMembersSet(@PathVariable("userId") Long userId, @PathVariable("setId") Long setId){
        Set set = setService.addMember(userId,setId);
        settingsService.createSettings(userId, setId);
        return DTOMapper.INSTANCE.convertEntityToSetGetDTO(set);
    }

// All Post Mappings:

    // Add a new Set to the database
    @PostMapping("/sets")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public SetGetDTO createSet(@RequestBody SetPostDTO setPostDTO) {

        // convert API set to internal representation
        Set setInput = DTOMapper.INSTANCE.convertSetPostDTOtoEntity(setPostDTO);

        // create set
        Set createdSet = setService.createSet(setInput);

        // create default setting file to be saved in repository
        settingsService.createSettings(createdSet.getUser(), createdSet.getSetId());

        // convert internal representation of set back to API
        return DTOMapper.INSTANCE.convertEntityToSetGetDTO(createdSet);
    }


// All Delete Mappings:

    // Delete a specific set of cards
    @DeleteMapping("/sets/{setId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSet(@PathVariable("setId") Long setId){
        setService.deleteSet(setId);
    }


    //Remove a specific member from the member list of a set
    @DeleteMapping("/sets/{userId}/{setId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SetGetDTO removeMember(@PathVariable("userId") Long userId, @PathVariable("setId") Long setId){
        Set set = setService.removeMember(userId,setId);
        return DTOMapper.INSTANCE.convertEntityToSetGetDTO(set);
    }

}
