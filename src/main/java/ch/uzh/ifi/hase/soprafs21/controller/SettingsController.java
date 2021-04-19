package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Settings;
import ch.uzh.ifi.hase.soprafs21.rest.dto.SettingsGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    public SettingsGetDTO getSettingsBySetIdandUserID (@PathVariable Long userId, @PathVariable Long setId) {

        // Get specific set by setId and userId
        Settings settings = settingsService.getSettings(userId, setId);

        return DTOMapper.INSTANCE.convertEntityToSettingsGetDTO(settings);
    }
}




//set.setCards();


/// Call the settings service as well  Settinngs settinsgs=findBzUSerIDaAndSetID
//Settings settings;
// if(settings.getStudyStarred())  // apply filter criteria to filter only the starred one card

// List<Card>

// if  set.getCards().stream().filter(ele->)
// Convert from internal representation to API

