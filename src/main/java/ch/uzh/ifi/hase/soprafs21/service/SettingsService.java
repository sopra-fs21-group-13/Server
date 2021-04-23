package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Card;
import ch.uzh.ifi.hase.soprafs21.entity.Set;
import ch.uzh.ifi.hase.soprafs21.entity.Settings;
import ch.uzh.ifi.hase.soprafs21.repository.SetRepository;
import ch.uzh.ifi.hase.soprafs21.repository.SettingsRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.rest.dto.SettingsPostDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
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

    public Settings checkIfUserAndSetExist(SettingsPostDTO settingsPostDTO){

        Settings settings;

        if (!userRepository.existsById(settingsPostDTO.getUserID())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exist");
        } else if(!settingsRepository.existsByUserIDAndSetID(settingsPostDTO.getUserID(), settingsPostDTO.getSetID())) {
            createSettings(settingsPostDTO.getUserID(), settingsPostDTO.getSetID());
            settings = getSettings(settingsPostDTO.getUserID(),settingsPostDTO.getSetID());
        }
        else {
            //update Settingsfile
            settings = getSettings(settingsPostDTO.getUserID(),settingsPostDTO.getSetID());
            settings.setCardsShuffled(settingsPostDTO.getCardsShuffled());
            settings.setStudyStarred(settingsPostDTO.getStudyStarred());
            settings.setLastCardID(settingsPostDTO.getLastCardID());
            settings.setStarredCards(settingsPostDTO.getStarredCards());
            settings.setCardOrder(settingsPostDTO.getCardOrder());
        }
        return settings;
    }

    // Create Setting file
    public void createSettings(Long userId, Long setId){
        // Create default cardOrder arraylist by getting all cardIds
        List<Long> cardOrder =  new ArrayList<>();
        Set set = setRepository.findById(setId).get();
        List<Card> cards = set.getCards();
        for (Card card: cards) {
            cardOrder.add(card.getCardId());
        }

        //int[] cardOrderInt = IntStream.range(0, cardSetSize).toArray();
        //List<Integer> cardOrder = IntStream.of(cardOrderInt).boxed().collect(Collectors.toList());

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
        //in case of return setting file
        //return newSetting;
    }

}
