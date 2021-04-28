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
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

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
            updatedSetting.setLastCard(settings.getLastCard());
            updatedSetting.setMarkedCards(settings.getMarkedCards());
            updatedSetting.setSavedOrder(settings.getSavedOrder());
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
        List<Long> savedOrder =  new ArrayList<>();
        List<Card> cards = set.getCards();
        for (Card card: cards) {
            savedOrder.add(card.getCardId());
        }

        //Apply new StarredCards -> adjust for deleted cards
        updateStarredCards(savedOrder,settingFiles);


        // Set new default order in dependent setting files and adjust LastCardId
        for (Settings settings: settingFiles){
            settings.setSavedOrder(savedOrder);
            settings.setLastCard(savedOrder.get(0));
            settings.setStudyStarred(false);
            settings.setCardsShuffled(false);
        }
    }

    public void updateStarredCards(List<Long> newCardIds, List<Settings> oldSettings){
        // Intersection of cardIds in set and the old starred Cards
        // This will exclude all deleted cardIds in the starred list
        for (Settings oldSetting: oldSettings){
            List<Long> markedCardIds = oldSetting.getMarkedCards();
            List<Long> newMarkedCardIds = intersection(markedCardIds,newCardIds);
            oldSetting.setMarkedCards(newMarkedCardIds);
        }
    }

    // Helper Method
    public static List<Long> intersection(List<Long> l1, List<Long> l2) {
        return l1.stream().filter(l2::contains).collect(Collectors.toList());
    }

    // Create Setting file
    public void createSettings(Long userId, Long setId){
        // Create default cardOrder arraylist by getting all cardIds
        List<Long> cardOrder =  new ArrayList<>();
        if (isEmpty(setRepository.findById(setId))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Set not found for creating setting file");
        }
        Set set = setRepository.findById(setId).get();
        if (!setRepository.existsById(setId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Set not found for creating setting file");
        }
        List<Card> cards = set.getCards();
        for (Card card: cards) {
            cardOrder.add(card.getCardId());
        }

        // Create empty list for starredCards, since default set have no starred cards
        ArrayList<Long> markedCards = new ArrayList<>();

        //Creating instance and default settings
        Settings newSetting = new Settings();
        newSetting.setUserID(userId);
        newSetting.setSetID(setId);
        newSetting.setCardsShuffled(false);
        newSetting.setStudyStarred(false);
        newSetting.setLastCard(0L);
        newSetting.setSavedOrder(cardOrder);
        newSetting.setMarkedCards(markedCards);

        // save & flush to repo
        newSetting = settingsRepository.save(newSetting);
        settingsRepository.flush();

        log.debug("Created Information fo Setting File: {}", newSetting);
    }


}
