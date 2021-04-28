package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.SetStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Set;
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

import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * Set Service
 * This class is the "worker" and responsible for all functionality related to the flashcard set
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back to the caller.
 */

@Service
@Transactional
public class SetService {

    private final Logger log = LoggerFactory.getLogger(SetService.class);
    private final SetRepository setRepository;
    private final SettingsRepository settingsRepository;

    @Autowired
    public SetService(@Qualifier("setRepository") SetRepository setRepository, @Qualifier("userRepository") UserRepository userRepository, SettingsRepository settingsRepository) {
        this.setRepository = setRepository;
        this.settingsRepository = settingsRepository;
    }

    // Get all sets available -> not useful though
    public List<Set> getPublicSets() {
        return this.setRepository.findBySetStatus(SetStatus.PUBLIC);
    }


    // Get set by setId
    public Set getSetBySetId(Long setId){

        if (!isEmpty(setRepository.findBySetId(setId))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ain't no set with setId");
        }
        return setRepository.findBySetId(setId).get();
    }


    // Create a flashcard set
    public Set createSet(Set newSet) {
        if (newSet == null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Set is empty");
        }
        // saves the given entity but data is only persisted in the database once flush() is called
        newSet = setRepository.save(newSet);
        setRepository.flush();

        log.debug("Created Information for User: {}", newSet);
        return newSet;
    }


    // Edit a Flashcard Set
    public Set updateSet(Set set){
        Set updatedSet = getSetBySetId(set.getSetId());

        if (updatedSet == null) {
            String message = "The set with id: %s can't be found in the database.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(message, set.getSetId()));
        }
        else {
            if (set.getTitle() != null) {
                updatedSet.setTitle(set.getTitle());
            }
            if (set.getExplain() != null) {
                updatedSet.setExplain(set.getExplain());
            }
            if (set.getCards() != null) {
                updatedSet.setCards(set.getCards());
            }
            if (set.getSetCategory() != null) {
                updatedSet.setSetCategory(set.getSetCategory());
            }
            if (set.getSetStatus() != null) {
                updatedSet.setSetStatus(set.getSetStatus());
            }
            if (set.getPhoto() != null) {
                updatedSet.setPhoto(set.getPhoto());
            }
            if (set.getLiked() != null) {
                updatedSet.setLiked(set.getLiked());
            }
        }
        updatedSet = setRepository.saveAndFlush(updatedSet);

        return updatedSet;
    }

    // Delete a Flashcard Set -> irrevocable
    public void deleteSet(Long setId){
        setRepository.deleteById(setId);
        settingsRepository.deleteBySetID(setId);
    }

    /*
    // Not needed now
    // Get set that were created by user x
    public List<Set> getSetByUser(User user){
        return setRepository.findByUser(user);
    }
    */


    /* Probably obsolete -> since you can get all learn sets from calling a single user

    // SaveFile needs to be implemented first
    // Get learn sets of a user
    public List<Set> getLearnSetByUser(Long userId){
        User user = userRepository.findById(userId).get();
        // Get SaveFile and the List of setIds

        // Get List of actual Sets
        List<Set> learnSets = null;

        return learnSets;
    }

     */


}
