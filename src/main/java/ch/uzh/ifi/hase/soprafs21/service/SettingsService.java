package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Settings;
import ch.uzh.ifi.hase.soprafs21.repository.SettingsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Settings Service
 * This class is the "worker" and responsible for all functionality related to the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back to the caller.
 */

@Service
@Transactional
public class SettingsService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final SettingsRepository settingsRepository;

    @Autowired
    public SettingsService(@Qualifier("settingsRepository") SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    // Create Setting file
    public void createSettings(Long userId, Long setId, Integer cardSetSize){
        // Create default cardOrder arraylist
        int[] cardOrderInt = IntStream.range(0, cardSetSize).toArray();
        List<Integer> cardOrder = IntStream.of(cardOrderInt).boxed().collect(Collectors.toList());

        //Creating instance and default settings
        Settings newSetting = new Settings();
        newSetting.setUserID(userId);
        newSetting.setSetID(setId);
        newSetting.setCardsShuffled(false);
        newSetting.setStudyStarred(false);
        newSetting.setLastCardID(0L);
        newSetting.setCardOrder(cardOrder);

        // save & flush to repo
        newSetting = settingsRepository.save(newSetting);
        settingsRepository.flush();

        log.debug("Created Information fo Setting File: {}", newSetting);
        //in case of return setting file
        //return newSetting;
    }


    // Get Settingsfile
    public Settings getSettings(Long  userId, Long setID) {
        return this.settingsRepository.findByUserIDAndSetID(userId, setID);
    }

}
