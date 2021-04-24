package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Card;
import ch.uzh.ifi.hase.soprafs21.entity.Set;
import ch.uzh.ifi.hase.soprafs21.entity.Settings;
import ch.uzh.ifi.hase.soprafs21.repository.SetRepository;
import ch.uzh.ifi.hase.soprafs21.repository.SettingsRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

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
    private final SetRepository setRepository;
    private final UserRepository userRepository;


    @Autowired
    public SettingsService(@Qualifier("settingsRepository") SettingsRepository settingsRepository, @Qualifier("setRepository") SetRepository setRepository, @Qualifier("userRepository") UserRepository userRepository) {
        this.settingsRepository = settingsRepository;
        this.setRepository = setRepository;
        this.userRepository = userRepository;
    }

    // Get Settingsfile
    public Settings getSettings(Long  userId, Long setId) {
        return this.settingsRepository.findByUserIDAndSetID(userId, setId);
    }

    public List<Settings> getAllSettings() {
        return this.settingsRepository.findAll();
    }

    public Settings updateSettings(Settings settings){

        Settings updatedSetting;

        if (!userRepository.existsById(settings.getUserID())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exist");
        } else if(!settingsRepository.existsByUserIDAndSetID(settings.getUserID(), settings.getSetID())) {
            createSettings(settings.getUserID(), settings.getSetID());
            updatedSetting = getSettings(settings.getUserID(),settings.getSetID());
        }
        else {
            //update Settingsfile
            updatedSetting = getSettings(settings.getUserID(),settings.getSetID());
            updatedSetting.setCardsShuffled(settings.getCardsShuffled());
            updatedSetting.setStudyStarred(settings.getStudyStarred());
            updatedSetting.setLastCardID(settings.getLastCardID());
            updatedSetting.setStarredCards(settings.getStarredCards());
            updatedSetting.setCardOrder(settings.getCardOrder());
        }
        return updatedSetting;
    }


    public void updateCardOrder(Set set){
        // Throw Error when Set doesn't exist
        if (!setRepository.existsById(set.getSetId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Set doesn't exist");
        }
        // Get List of all settings with given setId
        List<Settings> settingFiles = settingsRepository.findAllBySetID(set.getSetId());
        if(settingFiles.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Settingfiles were found when updating set");
        }

        // Create default cardOrder List
        List<Long> cardOrder =  new ArrayList<>();
        List<Card> cards = set.getCards();
        for (Card card: cards) {
            cardOrder.add(card.getCardId());
        }

        // Set new default order in dependent setting files
        for (Settings settings: settingFiles){
            settings.setCardOrder(cardOrder);
        }
    }

    // Create Setting file
    public void createSettings(Long userId, Long setId){
        // Create default cardOrder arraylist by getting all cardIds
        List<Long> cardOrder =  new ArrayList<>();
        Set set = setRepository.findById(setId).get();
        if (set == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Set not found for creating setting file");
        }
        List<Card> cards = set.getCards();
        for (Card card: cards) {
            cardOrder.add(card.getCardId());
        }

        // Create empty list for starredCards, sincene default set have no starred cards
        ArrayList<Long> starredCards = new ArrayList<>();

        //Creating instance and default settings
        Settings newSetting = new Settings();
        newSetting.setUserID(userId);
        newSetting.setSetID(setId);
        newSetting.setCardsShuffled(false);
        newSetting.setStudyStarred(false);
        newSetting.setLastCardID(0L);
        newSetting.setCardOrder(cardOrder);
        newSetting.setStarredCards(starredCards);

        // save & flush to repo
        newSetting = settingsRepository.save(newSetting);
        settingsRepository.flush();

        log.debug("Created Information fo Setting File: {}", newSetting);
    }


}
