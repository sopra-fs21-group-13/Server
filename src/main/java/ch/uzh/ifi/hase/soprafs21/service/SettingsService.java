package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Settings;
import ch.uzh.ifi.hase.soprafs21.repository.SettingsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Settings getSettings(Long  userId, Long setID) {
        return this.settingsRepository.findByUserIDAndSetID(userId, setID);
    }


}
