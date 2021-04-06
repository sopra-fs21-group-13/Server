package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.SetOrder;
import ch.uzh.ifi.hase.soprafs21.constant.SetStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Set;
import ch.uzh.ifi.hase.soprafs21.repository.SetRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    private final UserRepository userRepository;

    @Autowired
    public SetService(@Qualifier("setRepository") SetRepository setRepository, UserRepository userRepository) {
        this.setRepository = setRepository;
        this.userRepository = userRepository;
    }

    // Get all sets available -> not useful though
    public List<Set> getPublicSets() {
        return this.setRepository.findBySetStatus(SetStatus.PUBLIC);
    }


    /*
    // Not needed now
    // Get set that were created by user x
    public List<Set> getSetByUser(User user){
        return setRepository.findByUser(user);
    }
    */


    /* Probably osolete -> since you can get all learn sets from calling a single user

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


    // Get set by setId
    public Set getSetBySetId(Long setId){
        return setRepository.findBySetId(setId).get();
    }


    // Create a flashcard set
    public Set createSet(Set newSet) {
        // Assign non-nullable properties
        newSet.setSetOrder(SetOrder.NORMAL);

        // saves the given entity but data is only persisted in the database once flush() is called
        newSet = setRepository.save(newSet);
        setRepository.flush();

        log.debug("Created Information for User: {}", newSet);
        return newSet;
    }


    // Edit a Flashcard Set
    public void updateSet(Set newSet){

    }


    // Delete a Flashcard Set -> irrevocable
    public void deleteSet(Long setId){
        setRepository.deleteById(setId);
    }

}
