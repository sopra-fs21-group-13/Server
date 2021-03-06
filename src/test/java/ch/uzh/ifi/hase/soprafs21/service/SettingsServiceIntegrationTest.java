package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.SetCategory;
import ch.uzh.ifi.hase.soprafs21.constant.SetStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Card;
import ch.uzh.ifi.hase.soprafs21.entity.Set;
import ch.uzh.ifi.hase.soprafs21.entity.Settings;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.SetRepository;
import ch.uzh.ifi.hase.soprafs21.repository.SettingsRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
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
        assertEquals(testSettings.getUserID(), createdSettings.getUserID());
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
        assertThrows(ResponseStatusException.class, () -> settingsService.createSettings(createdUser.getUserId(),2L));
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
        assertEquals(testSettings.getUserID(), createdSettings.getUserID());
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
        assertEquals(testSettings.getUserID(), createdSettings.get(0).getUserID());
        assertEquals(testSettings.getSetID(), createdSettings.get(0).getSetID());
        assertEquals(testSettings.getCardsShuffled(), createdSettings.get(0).getCardsShuffled());
        assertNotNull(createdSettings.get(0).getSettingsId());
    }

    @Test
    public void checkUpdateSettings_ValidInput_success(){
        // given
        assertNull(userRepository.findByUsername("testUsername"));
        assertTrue(setRepository.findAll().isEmpty());
        assertTrue(settingsRepository.findAll().isEmpty());
        // cardList
        List<Card> cardList = new ArrayList<>();
        Card card1 = new Card();
        card1.setAnswer("answer");
        card1.setQuestion("question");
        Card card2 = new Card();
        card2.setAnswer("answer2");
        card2.setQuestion("question2");
        cardList.add(card1);
        cardList.add(card2);
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
        testSet.setCards(cardList);
        testSet.setSetCategory(SetCategory.BIOLOGY);
        testSet.setSetStatus(SetStatus.PUBLIC);
        testSet.setExplain("explain");
        testSet.setPhoto("photo");
        // when
        Set createdSet = setService.createSet(testSet);
        settingsService.createSettings(createdUser.getUserId(),createdSet.getSetId());

        Card card3 = new Card();
        card3.setAnswer("answer3");
        card3.setQuestion("question3");
        cardList.add(card3);

        List<Long> savedOrder = new ArrayList<>();
        savedOrder.add(1L);
        savedOrder.add(2L);
        savedOrder.add(3L);

        //setUp testSetting
        Settings testSettings = new Settings();
        testSettings.setUserID(createdUser.getUserId());
        testSettings.setSetID(createdSet.getSetId());
        testSettings.setCardsShuffled(true);
        testSettings.setStudyStarred(true);
        testSettings.setSavedOrder(savedOrder);

        Settings updatedSettings = settingsService.updateSettings(testSettings);

        assertEquals(testSettings.getUserID(), updatedSettings.getUserID());
        assertEquals(testSettings.getSetID(), updatedSettings.getSetID());
        assertEquals(testSettings.getCardsShuffled(), updatedSettings.getCardsShuffled());
        assertEquals(testSettings.getStudyStarred(), updatedSettings.getStudyStarred());
        assertEquals(testSettings.getSavedOrder(), updatedSettings.getSavedOrder());
        assertNotNull(updatedSettings.getSettingsId());
    }


    @Test
    public void checkUpdateSettings_updateCards_success(){
        // given
        assertNull(userRepository.findByUsername("testUsername"));
        assertTrue(setRepository.findAll().isEmpty());
        assertTrue(settingsRepository.findAll().isEmpty());
        // cardList
        List<Card> cardList = new ArrayList<>();
        Card card1 = new Card();
        card1.setAnswer("answer");
        card1.setQuestion("question");
        Card card2 = new Card();
        card2.setAnswer("answer2");
        card2.setQuestion("question2");
        cardList.add(card1);
        cardList.add(card2);
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
        testSet.setCards(cardList);
        testSet.setSetCategory(SetCategory.BIOLOGY);
        testSet.setSetStatus(SetStatus.PUBLIC);
        testSet.setExplain("explain");
        testSet.setPhoto("photo");
        // when
        Set createdSet = setService.createSet(testSet);
        settingsService.createSettings(createdUser.getUserId(),createdSet.getSetId());

        Settings createdSettings = settingsRepository.findByUserIDAndSetID(createdUser.getUserId(),createdSet.getSetId());

        Card card3 = new Card();
        card3.setAnswer("answer3");
        card3.setQuestion("question3");
        cardList.add(card3);
        createdSet.setCards(cardList);

        settingsService.updateCardOrder(createdSet);

        List<Long> savedOrder = new ArrayList<>();
        savedOrder.add(1L);
        savedOrder.add(2L);
        savedOrder.add(3L);

        //setUp testSetting
        Settings testSettings = new Settings();
        testSettings.setUserID(createdUser.getUserId());
        testSettings.setSetID(createdSet.getSetId());
        testSettings.setCardsShuffled(false);
        testSettings.setStudyStarred(false);
        testSettings.setSavedOrder(savedOrder);

        Settings updatedSettings = settingsService.updateSettings(testSettings);

        assertEquals(testSettings.getUserID(), updatedSettings.getUserID());
        assertEquals(testSettings.getSetID(), updatedSettings.getSetID());
        assertEquals(testSettings.getCardsShuffled(), updatedSettings.getCardsShuffled());
        assertEquals(testSettings.getStudyStarred(), updatedSettings.getStudyStarred());
        assertEquals(testSettings.getSavedOrder(), updatedSettings.getSavedOrder());
        assertNotNull(updatedSettings.getSettingsId());
    }


}
