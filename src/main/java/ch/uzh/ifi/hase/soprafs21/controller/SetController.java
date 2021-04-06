package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Set;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.SetGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.SetPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.SetService;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
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
    private UserService userService;

// All Get Mappings:

    // Get all public sets
    @GetMapping("/sets")
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
    };

    // Get flashcardset with setId
    @GetMapping("/sets/{setId}")
    public SetGetDTO getSetBySetId(@PathVariable("setId") Long setId) {
        // Get specific set by setId
        Set set = setService.getSetBySetId(setId);
        // Convert from internal representation to API
        return DTOMapper.INSTANCE.convertEntityToSetGetDTO(set);
    }

    // Get Sets of a user
    @GetMapping("/users/{userId}/learnsets")
    public void getLearnSetsByUser(@PathVariable("userId") Long userId) {
        // Get specific user by userId
        User user = userService.getUser(userId);
        //

    }

/*   We dont need this

    // Get created sets of a specific user
    @GetMapping("/sets/users/{userId}/set")
    public List<Set> getSetByUser(@PathVariable("userId") Long userId) {
        System.out.println(userId);
        // Get User by ID
        User user = userService.getUser(userId);
        // Get all sets of user
        return setService.getSetByUser(user);
    }
 */

// All Put Mappings:

    // Update a specific set of cards




// All Post Mappings:

    // Add a new Set to the db
    @PostMapping("/sets")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public SetGetDTO createSet(@RequestBody SetPostDTO setPostDTO) {

        // convert API set to internal representation
        Set setInput = DTOMapper.INSTANCE.convertSetPostDTOtoEntity(setPostDTO);

        // create set
        Set createdSet = setService.createSet(setInput);

        // convert internal representation of set back to API
        return DTOMapper.INSTANCE.convertEntityToSetGetDTO(createdSet);
    }


// All Delete Mappings:

    // Delete a specific set of cards
    @DeleteMapping("/sets/{setId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void deleteSet(@PathVariable("setId") Long setId){
        setService.deleteSet(setId);
    }

}
