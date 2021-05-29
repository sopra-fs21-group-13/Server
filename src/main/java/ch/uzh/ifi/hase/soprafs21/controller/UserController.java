package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
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
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {

        this.userService = userService;
    }

    /**
    @Autowired
    private SettingsService settingsService;
     */

// All Get Mappings:

    // Get all users
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserGetDTO> getAllUsers() {
        // fetch all users in the internal representation
        List<User> users = userService.getUsers();
        List<UserGetDTO> userGetDTOs = new ArrayList<>();

        // convert each user to the API representation
        for (User user : users) {
            userGetDTOs.add(DTOMapper.INSTANCE.convertEntityToUserGetDTO(user));
        }
        return userGetDTOs;
    }

    // Get all users that are online
    @GetMapping("/users/online")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserGetDTO> getAllOnlineUsers() {
        // fetch all users in the internal representation
        List<User> users = userService.getOnlineUsers();
        List<UserGetDTO> userGetDTOs = new ArrayList<>();

        // convert each user to the API representation
        for (User user : users) {
            userGetDTOs.add(DTOMapper.INSTANCE.convertEntityToUserGetDTO(user));
        }
        return userGetDTOs;
    }

    // Get a user by userId
    @GetMapping ("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserGetDTO getUserByID(@PathVariable Long userId) {

        User loginUser = userService.getUser(userId);

        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(loginUser);
    }

// All Post Mappings:

    // create a user entity in the database
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserGetDTO createUser(@RequestBody UserPostDTO userPostDTO) {
        // convert API user to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        // create user
        User createdUser = userService.createUser(userInput);

        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(createdUser);
    }

    // Log a user with valid inputs
    @PostMapping("/users/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO loginUser(@RequestBody UserPostDTO userPostDTO) {

        // convert API user to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);
        //System.out.println("Login user-------"+userInput);

        // check if user exists
        User loginUser = userService.checkForLogin(userInput);
        //System.out.println("Login user-------"+loginUser);

        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(loginUser);
    }

    // Create user entity for google login
    @PostMapping("/users/socialLogin")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO socialLoginUser(@RequestBody UserPostDTO userPostDTO) {

        // convert API user to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);
        //System.out.println("Login user-------"+userInput);

        // check if user exists
        User loginUser = userService.upserd(userInput);

        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(loginUser);
    }


// All Put Mappings:

    // Update the user entity
    @PutMapping ("/users")
    @ResponseStatus(HttpStatus.OK)
    public UserGetDTO getUserByID(@RequestBody UserPostDTO userPostDTO) {

       //User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);
        User updatedUser = userService.updateUser(userPostDTO);


        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(updatedUser);
    }

    // Log a specific user out
    @PutMapping ("/users/logout/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserGetDTO logoutUser(@PathVariable Long userId) {

        //User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);
        User updatedUser = userService.logoutUser(userId);

        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(updatedUser);
    }

// All Delete Mappings:

    // Delete a specific User
    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSet(@PathVariable Long userId){
        userService.deleteUser(userId);
    }

}
