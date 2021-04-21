package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Settings;
import ch.uzh.ifi.hase.soprafs21.rest.dto.SettingsGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.SettingsPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Card Controller
 * This class is responsible for handling all REST request that are related to the user.
 * The controller will receive the request and delegate the execution to the UserService and finally return the result.
 */

@RestController
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    // Get Settings with setId and UserID
    @GetMapping("/settings/{userId}/{setId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SettingsGetDTO getSettingsBySetIdandUserID (@PathVariable("userId") Long userId, @PathVariable("setId") Long setId) {

        // Get specific set by setId and userId
        Settings settings = settingsService.getSettings(userId, setId);

        return DTOMapper.INSTANCE.convertEntityToSettingsGetDTO(settings);
    }

    @PostMapping("/settings")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public SettingsGetDTO createSettings(@RequestBody SettingsPostDTO settingsPostDTO) {

        Settings UpdatedSettings = settingsService.checkIfUserAndSetExist(settingsPostDTO);

        // convert internal representation of set back to API
        return DTOMapper.INSTANCE.convertEntityToSettingsGetDTO(UpdatedSettings);
    }
}




//set.setCards();


/// Call the settings service as well  Settinngs settinsgs=findBzUSerIDaAndSetID
//Settings settings;
// if(settings.getStudyStarred())  // apply filter criteria to filter only the starred one card

// List<Card>

// if  set.getCards().stream().filter(ele->)
// Convert from internal representation to API

