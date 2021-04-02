package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs21.entity.FlashCardSet;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.FlashCardSetRepository;
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
 * User Service
 * This class is the "worker" and responsible for all functionality related to the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back to the caller.
 */
@Service
@Transactional
public class FlashCardSetService {
    @Autowired
    private FlashCardSetRepository flashCardSetRepository;

    public FlashCardSet create(FlashCardSet flashCardSet) {

        flashCardSet = flashCardSetRepository.save(flashCardSet);
       flashCardSetRepository.flush();
       return flashCardSet;
    }

    public List<FlashCardSet> getByUser(User user) {
        return flashCardSetRepository.findByUser(user);
    }
}
