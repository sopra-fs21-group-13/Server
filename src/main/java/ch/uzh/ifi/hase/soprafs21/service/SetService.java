package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.SetOrder;
import ch.uzh.ifi.hase.soprafs21.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Card;
import ch.uzh.ifi.hase.soprafs21.entity.Set;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.SetRepository;
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
import java.util.UUID;

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

    @Autowired
    public SetService(@Qualifier("setRepository") SetRepository setRepository) {
        this.setRepository = setRepository;
    }

    // Get all sets available -> not useful though
    public List<Set> getSets() {
        return this.setRepository.findAll();
    }

    // Get sets by User
    public List<Set> getSetByUser(User user){
        return setRepository.findByUser(user);
    }

    // Get set by setId
    public Set getSetBySetId(Long setId){
        return setRepository.findBySetId(setId).get();
    }

    // Create a flashcard set
    public Set createSet(Set newSet) {
        // Assign non-nullable properties
        newSet.setSetOrder(SetOrder.NORMAL);
        // Not sure yet how to pass userId yet
        // newSet.setUserId(userId);

        // Method to Create Flashcards and add them to List in Set



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





    /**
     * This is a helper method that will create the flashcards and add them to the list within the set
     *
     * @param card
     * @throws org.springframework.web.server.ResponseStatusException
     * @see Card
     */

    private void createCards(Card card) {

    }


}
