package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Settings;
import ch.uzh.ifi.hase.soprafs21.rest.dto.SettingsGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.SettingsPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Settings Controller
 * This class is responsible for handling all REST request that are related to the settings.
 * The controller will receive the request and delegate the execution to the SettingsService and finally return the result.
 */

@RestController
public class SettingsController {

    @Autowired
    private SettingsService settingsService;


// All Get Mappings:

    @GetMapping("/settings")
    @ResponseStatus(HttpStatus.OK)
    public List<SettingsGetDTO> getAllSettingss(){
        // fetch all sets in the internal representation
        List<Settings> AllSettings = settingsService.getAllSettings();
        List<SettingsGetDTO> settingsGetDTOS = new ArrayList<>();

        // convert each user to the API representation
        for (Settings setting : AllSettings){
            settingsGetDTOS.add(DTOMapper.INSTANCE.convertEntityToSettingsGetDTO(setting));
        }

        // return list of all public settings
        return settingsGetDTOS;
    }

    // Get Settings with setId and UserID
    @GetMapping("/settings/{userId}/{setId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SettingsGetDTO getSettingsBySetIdandUserID (@PathVariable("userId") Long userId, @PathVariable("setId") Long setId) {

        // Get specific set by setId and userId
        Settings settings = settingsService.getSettings(userId, setId);

        return DTOMapper.INSTANCE.convertEntityToSettingsGetDTO(settings);
    }

// All Post Mappings:

    // create Settings entity
    @PostMapping("/settings")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public SettingsGetDTO createSettings(@RequestBody SettingsPostDTO settingsPostDTO) {

        Settings settingInput = DTOMapper.INSTANCE.convertSettingsPostDTOtoEntity(settingsPostDTO);

        Settings UpdatedSettings = settingsService.updateSettings(settingInput);

        // convert internal representation of set back to API
        return DTOMapper.INSTANCE.convertEntityToSettingsGetDTO(UpdatedSettings);
    }
}

