package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.SetCategory;
import ch.uzh.ifi.hase.soprafs21.constant.SetStatus;
import ch.uzh.ifi.hase.soprafs21.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Card;
import ch.uzh.ifi.hase.soprafs21.entity.Set;
import ch.uzh.ifi.hase.soprafs21.entity.Settings;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.SetRepository;
import ch.uzh.ifi.hase.soprafs21.repository.SettingsRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPostDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@WebAppConfiguration
@SpringBootTest
public class SettingsServiceIntegrationTest {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Qualifier("setRepository")
    @Autowired
    private SetRepository setRepository;

    @Autowired
    private SetService setService;

    @Qualifier("settingsRepository")
    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private SettingsService settingsService;


    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        setRepository.deleteAll();
        settingsRepository.deleteAll();
    }

    @Test
    public void createSettings_validInputs_success() {
        // given
        assertNull(userRepository.findByUsername("testUsername"));
        assertTrue(setRepository.findAll().isEmpty());
        assertTrue(settingsRepository.findAll().isEmpty());
        // cardList
        List<Card> emptyList = new ArrayList<>();
        // user setup
        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        User createdUser = userService.createUser(testUser);
        // set setup
        Set testSet = new Set();
        testSet.setTitle("title");
        testSet.setUser(createdUser);
        testSet.setCards(emptyList);
        testSet.setSetCategory(SetCategory.BIOLOGY);
        testSet.setSetStatus(SetStatus.PUBLIC);
        testSet.setExplain("explain");
        testSet.setPhoto("photo");
        // when
        Set createdSet = setService.createSet(testSet);
        settingsService.createSettings(createdUser.getUserId(),createdSet.getSetId());

        //setUp testSetting
        Settings testSettings = new Settings();
        testSettings.setUserID(createdUser.getUserId());
        testSettings.setSetID(createdSet.getSetId());
        testSettings.setCardsShuffled(false);
        testSettings.setStudyStarred(false);

        Settings createdSettings = settingsRepository.findByUserIDAndSetID(createdUser.getUserId(),createdSet.getSetId());

        // then
        assertEquals(testSettings.getUserID(), createdSettings.getSetID());
        assertEquals(testSettings.getSetID(), createdSettings.getSetID());
        assertEquals(testSettings.getCardsShuffled(), createdSettings.getCardsShuffled());
        assertNotNull(createdSettings.getSettingsId());
    }

    @Test
    public void createSettings_wrongSetId_throwsException() {
        // given
        assertNull(userRepository.findByUsername("testUsername"));
        assertTrue(setRepository.findAll().isEmpty());
        assertTrue(settingsRepository.findAll().isEmpty());
        // cardList
        List<Card> emptyList = new ArrayList<>();
        // user setup
        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        User createdUser = userService.createUser(testUser);
        // set setup
        Set testSet = new Set();
        testSet.setTitle("title");
        testSet.setUser(createdUser);
        testSet.setCards(emptyList);
        testSet.setSetCategory(SetCategory.BIOLOGY);
        testSet.setSetStatus(SetStatus.PUBLIC);
        testSet.setExplain("explain");
        testSet.setPhoto("photo");
        // when
        setService.createSet(testSet);

        // check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> settingsService.createSettings(createdUser.getUserId(),3L));
    }

    @Test
    public void createSettings_wrongUserId_throwsException() {
        // given
        assertNull(userRepository.findByUsername("testUsername"));
        assertTrue(setRepository.findAll().isEmpty());
        assertTrue(settingsRepository.findAll().isEmpty());
        // cardList
        List<Card> emptyList = new ArrayList<>();
        // user setup
        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        User createdUser = userService.createUser(testUser);
        // set setup
        Set testSet = new Set();
        testSet.setTitle("title");
        testSet.setUser(createdUser);
        testSet.setCards(emptyList);
        testSet.setSetCategory(SetCategory.BIOLOGY);
        testSet.setSetStatus(SetStatus.PUBLIC);
        testSet.setExplain("explain");
        testSet.setPhoto("photo");
        // when
        Set createdSet = setService.createSet(testSet);

        // check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> settingsService.createSettings(2L,createdSet.getSetId()));
    }

    @Test
    public void checkGetSettings_ValidInput_success() {
        // given
        assertNull(userRepository.findByUsername("testUsername"));
        assertTrue(setRepository.findAll().isEmpty());
        assertTrue(settingsRepository.findAll().isEmpty());
        // cardList
        List<Card> emptyList = new ArrayList<>();
        // user setup
        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        User createdUser = userService.createUser(testUser);
        // set setup
        Set testSet = new Set();
        testSet.setTitle("title");
        testSet.setUser(createdUser);
        testSet.setCards(emptyList);
        testSet.setSetCategory(SetCategory.BIOLOGY);
        testSet.setSetStatus(SetStatus.PUBLIC);
        testSet.setExplain("explain");
        testSet.setPhoto("photo");
        // when
        Set createdSet = setService.createSet(testSet);
        settingsService.createSettings(createdUser.getUserId(),createdSet.getSetId());

        //setUp testSetting
        Settings testSettings = new Settings();
        testSettings.setUserID(createdUser.getUserId());
        testSettings.setSetID(createdSet.getSetId());
        testSettings.setCardsShuffled(false);
        testSettings.setStudyStarred(false);

        Settings createdSettings = settingsService.getSettings(createdUser.getUserId(),createdSet.getSetId());
        // then
        assertEquals(testSettings.getUserID(), createdSettings.getSetID());
        assertEquals(testSettings.getSetID(), createdSettings.getSetID());
        assertEquals(testSettings.getCardsShuffled(), createdSettings.getCardsShuffled());
        assertNotNull(createdSettings.getSettingsId());
    }

    @Test
    public void checkGetSettings_WrongInputs_Throw() {
        // given
        assertNull(userRepository.findByUsername("testUsername"));
        assertTrue(setRepository.findAll().isEmpty());
        assertTrue(settingsRepository.findAll().isEmpty());
        // cardList
        List<Card> emptyList = new ArrayList<>();
        // user setup
        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        User createdUser = userService.createUser(testUser);
        // set setup
        Set testSet = new Set();
        testSet.setTitle("title");
        testSet.setUser(createdUser);
        testSet.setCards(emptyList);
        testSet.setSetCategory(SetCategory.BIOLOGY);
        testSet.setSetStatus(SetStatus.PUBLIC);
        testSet.setExplain("explain");
        testSet.setPhoto("photo");
        // when
        Set createdSet = setService.createSet(testSet);
        settingsService.createSettings(createdUser.getUserId(),createdSet.getSetId());

        //setUp testSetting
        Settings testSettings = new Settings();
        testSettings.setUserID(createdUser.getUserId());
        testSettings.setSetID(createdSet.getSetId());
        testSettings.setCardsShuffled(false);
        testSettings.setStudyStarred(false);

        // throw Exception catcher
        assertThrows(ResponseStatusException.class, () -> settingsService.getSettings(2L,2L));

    }

    @Test
    public void checkGetAllSettings_ValidInput_success() {
        // given
        assertNull(userRepository.findByUsername("testUsername"));
        assertTrue(setRepository.findAll().isEmpty());
        assertTrue(settingsRepository.findAll().isEmpty());
        // cardList
        List<Card> emptyList = new ArrayList<>();
        // user setup
        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        User createdUser = userService.createUser(testUser);
        // set setup
        Set testSet = new Set();
        testSet.setTitle("title");
        testSet.setUser(createdUser);
        testSet.setCards(emptyList);
        testSet.setSetCategory(SetCategory.BIOLOGY);
        testSet.setSetStatus(SetStatus.PUBLIC);
        testSet.setExplain("explain");
        testSet.setPhoto("photo");
        // when
        Set createdSet = setService.createSet(testSet);
        settingsService.createSettings(createdUser.getUserId(),createdSet.getSetId());

        //setUp testSetting
        Settings testSettings = new Settings();
        testSettings.setUserID(createdUser.getUserId());
        testSettings.setSetID(createdSet.getSetId());
        testSettings.setCardsShuffled(false);
        testSettings.setStudyStarred(false);

        List<Settings> createdSettings = settingsService.getAllSettings();

        // then
        assertEquals(testSettings.getUserID(), createdSettings.get(0).getSetID());
        assertEquals(testSettings.getSetID(), createdSettings.get(0).getSetID());
        assertEquals(testSettings.getCardsShuffled(), createdSettings.get(0).getCardsShuffled());
        assertNotNull(createdSettings.get(0).getSettingsId());
    }

}
