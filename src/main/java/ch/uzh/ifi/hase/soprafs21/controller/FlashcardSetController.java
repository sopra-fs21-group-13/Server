package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.FlashCardSet;
import ch.uzh.ifi.hase.soprafs21.entity.Set;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPostDTO;
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
public class FlashcardSetController {

    @Autowired
    private SetService setService;

    @Autowired
    private UserService userService;
/*
    @PostMapping("/sets")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Set createSet(@RequestParam SetPostDTO setPostDTO) {
        //
        Set flashCardSet = new FlashCardSet();
        Set.setName(name);
        Set.setUser(userService.getUser(userId));

        return setService.createSet(flashCardSet);

    }

    @GetMapping("/sets/{userId}")
    public Set createUser(@PathVariable("userId") Long userId) {
        System.out.println(userId);
        // Get specific set of user
        return setService.getbySetId(userId);
    }

 */
}
