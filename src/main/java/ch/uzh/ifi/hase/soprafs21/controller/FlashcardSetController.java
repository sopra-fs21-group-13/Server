package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.FlashCardSet;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.FlashCardSetService;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User Controller
 * This class is responsible for handling all REST request that are related to the user.
 * The controller will receive the request and delegate the execution to the UserService and finally return the result.
 */
@RestController
public class FlashcardSetController {

    @Autowired
    private FlashCardSetService flashCardSetService;

    @Autowired
    private UserService userService;

    @PostMapping("/CreateFlashcardSet")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public FlashCardSet createUser(@RequestParam Long userId, @RequestParam String name) {
        FlashCardSet flashCardSet=new FlashCardSet();
        flashCardSet.setName(name);
        flashCardSet.setUser(userService.getUser(userId));

        return flashCardSetService.create(flashCardSet);

    }

    @GetMapping("/FindByUserID/{userId}")

    public List<FlashCardSet> createUser(@PathVariable("userId") Long userId) {
        System.out.println(userId);

        return flashCardSetService.getByUser(userService.getUser(userId));

    }

}
