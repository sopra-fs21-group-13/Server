package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.SetStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Set;
import ch.uzh.ifi.hase.soprafs21.entity.User;
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
import java.util.Optional;

import static java.util.Objects.isNull;

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
    private final SettingsRepository settingsRepository;

    @Autowired
    public SetService(@Qualifier("setRepository") SetRepository setRepository, @Qualifier("userRepository") UserRepository userRepository, SettingsRepository settingsRepository) {
        this.setRepository = setRepository;
        this.userRepository = userRepository;
        this.settingsRepository = settingsRepository;
    }

    String errorSetId = "No set exists with input set id.";
    String errorUserId = "No User with input id exists.";

    // Get all sets available -> not useful though
    public List<Set> getPublicSets() {
        return this.setRepository.findBySetStatus(SetStatus.PUBLIC);
    }

    // Get set by setId
    public Set getSetBySetId(Long setId){
        Optional<Set> checkSet = setRepository.findBySetId(setId);
        if (checkSet.isPresent()){
            return checkSet.get();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(errorSetId));
        }
    }

    // Create a flashcard set
    public Set createSet(Set newSet){
        if (checkSet(newSet)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Set is empty");
        }
        List<User> initialMember = new ArrayList<>();
        newSet.setMembers(initialMember);

        Optional<User> newUser = userRepository.findById(newSet.getUser());
        if (newUser.isPresent()){
            initialMember.add(newUser.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(errorUserId));
        }
        if (newSet.getMembers().isEmpty()){
            newSet.setMembers(initialMember);
        }
        // saves the given entity but data is only persisted in the database once flush() is called
        newSet = setRepository.save(newSet);
        setRepository.flush();

        log.debug("Created Information for User: {}", newSet);
        return newSet;
    }

    // Check for completeness of set
    public boolean checkSet(Set set){
        if (isNull(set.getTitle())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Set has no Title");
        }
        if ( isNull(set.getExplain())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Set has no Explanation");
        }
        if (isNull(set.getUser())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Set has no User");
        }
        if (isNull(set.getSetCategory())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Set has no SetCategory");
        }
        if (isNull(set.getSetStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Set has no SetStatus");
        }
        return false;
    }

    // Edit a Flashcard Set
    public Set updateSet(Set set){
        Set updatedSet = getSetBySetId(set.getSetId());

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
        // Save & Flush
        updatedSet = setRepository.save(updatedSet);
        setRepository.flush();

        return updatedSet;
    }

    // Add member to set
    public Set addMember(Long userId, Long setId){

        // find user with id
        User newUser;
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            newUser = user.get();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(errorUserId));
        }

        // find set with id
        Set newSet;
        Optional<Set> set = setRepository.findBySetId(setId);
        if (set.isPresent()){
            newSet = set.get();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(errorSetId));
        }

        List<Long> memberIds = newSet.getMembers();
        List<User> members = new ArrayList<>();
        for (Long memberId:memberIds){
            // Get user by id
            Optional<User> createdUser = userRepository.findById(memberId);
            if (createdUser.isPresent()){
                members.add(createdUser.get());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(errorUserId));
            }
        }
        members.add(newUser);
        newSet.setMembers(members);
        return newSet;
    }


    // Delete a Flashcard Set -> irrevocable
    public void deleteSet(Long setId){
        if (!setRepository.existsById(setId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Set with this SetId those not exist");
        }
        setRepository.deleteById(setId);
        settingsRepository.deleteBySetID(setId);
    }

    // Delete a Member from the set
    public Set removeMember(Long userId, Long setId){

        // Get set by Id
        Set newSet;
        Optional<Set> set = setRepository.findBySetId(setId);
        if (set.isPresent()){
            newSet = set.get();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(errorSetId));
        }

        if (newSet.getUser().equals(userId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can not remove the creator of a list from the Member List");
        }
        List<Long> memberIds = newSet.getMembers();
        List<User> members = new ArrayList<>();
        for (Long memberId:memberIds){
            if (!memberId.equals(userId)){
                Optional<User> createdUser = userRepository.findById(memberId);
                if (createdUser.isPresent()){
                    members.add(createdUser.get());
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(errorUserId));
                }
            }
        }
        newSet.setMembers(members);
        settingsRepository.deleteByUserIDAndSetID(userId,setId);
        return newSet;
    }

}
