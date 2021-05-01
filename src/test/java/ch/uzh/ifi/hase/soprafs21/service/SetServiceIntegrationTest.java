package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.SetCategory;
import ch.uzh.ifi.hase.soprafs21.constant.SetStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Card;
import ch.uzh.ifi.hase.soprafs21.entity.Set;
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
public class SetServiceIntegrationTest {

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
    public void createSet_validInputs_success() {
        // given
        assertNull(userRepository.findByUsername("testUsername"));
        assertTrue(setRepository.findAll().isEmpty());
        // cardList
        List<Card> emptyList = new ArrayList<>();
        // user setup
        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        // set setup
        Set testSet = new Set();
        testSet.setTitle("title");
        testSet.setUser(testUser);
        testSet.setCards(emptyList);
        testSet.setSetCategory(SetCategory.BIOLOGY);
        testSet.setSetStatus(SetStatus.PUBLIC);
        testSet.setExplain("explain");
        testSet.setPhoto("photo");

        // when
        User createdUser = userService.createUser(testUser);
        Set createdSet = setService.createSet(testSet);

        // then
        assertEquals(createdSet.getTitle(), testSet.getTitle());
        assertEquals(createdSet.getUser(), testSet.getUser());
        assertEquals(createdSet.getCards(), testSet.getCards());
        assertNotNull(createdSet.getSetId());
        assertEquals(createdSet.getSetCategory(), testSet.getSetCategory());
        assertEquals(createdSet.getSetStatus(), testSet.getSetStatus());
        assertEquals(createdSet.getExplain(), testSet.getExplain());
        assertEquals(createdSet.getPhoto(), testSet.getPhoto());
    }

    @Test
    public void createSet_emptySet_throwsException() {
        //given
        assertTrue(setRepository.findAll().isEmpty());
        // card List
        List<Card> emptyList = new ArrayList<>();
        // user setup
        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        // set setup
        Set testSet = new Set();

        // check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> setService.createSet(testSet));
    }

    @Test
    public void createSet_NoTitle_throwsException() {
        //given
        assertTrue(setRepository.findAll().isEmpty());
        // card List
        List<Card> emptyList = new ArrayList<>();
        // user setup
        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        // set setup
        Set testSet = new Set();
        testSet.setUser(testUser);
        testSet.setCards(emptyList);
        testSet.setSetCategory(SetCategory.BIOLOGY);
        testSet.setSetStatus(SetStatus.PUBLIC);
        testSet.setExplain("explain");
        testSet.setPhoto("photo");
        // check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> setService.createSet(testSet));
    }

    @Test
    public void checkUpdateSet_ValidInput_success() {
        // given
        assertNull(userRepository.findByUsername("testUsername"));
        assertTrue(setRepository.findAll().isEmpty());
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
        testSet.setMembers(new ArrayList<>());
        Set createdSet = setService.createSet(testSet);
        // set2 setup
        Set testSet2 = new Set();
        testSet2.setSetId(createdSet.getSetId());
        testSet2.setTitle("title2");
        testSet2.setUser(createdUser);
        testSet2.setCards(emptyList);
        testSet2.setSetCategory(SetCategory.GERMAN);
        testSet2.setSetStatus(SetStatus.PRIVATE);
        testSet2.setExplain("explain2");
        testSet2.setPhoto("photo2");
        testSet2.setMembers(new ArrayList<>());

        // when
        Set updateSet = setService.updateSet(testSet2);

        // then
        assertEquals(testSet2.getTitle(), updateSet.getTitle());
        assertEquals(testSet2.getUser(), updateSet.getUser());
        assertEquals(testSet2.getCards(), updateSet.getCards());
        assertNotNull(testSet2.getSetId());
        assertEquals(testSet2.getSetCategory(), updateSet.getSetCategory());
        assertEquals(testSet2.getSetStatus(), updateSet.getSetStatus());
        assertEquals(testSet2.getExplain(), updateSet.getExplain());
        assertEquals(testSet2.getPhoto(), updateSet.getPhoto());
        assertEquals(testSet2.getMembers(), updateSet.getMembers());
    }

    @Test
    public void checkUpdatedSet_emptySet_Throw(){
        // given
        assertNull(userRepository.findByUsername("testUsername"));
        assertTrue(setRepository.findAll().isEmpty());
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
        Set createdSet = setService.createSet(testSet);
        // set2 setup
        Set testSet2 = new Set();
        // when
        assertThrows(ResponseStatusException.class, () -> setService.updateSet(testSet2));
    }

    @Test
    public void checkDeleteSet_ValidInput_success(){
        // given
        assertNull(userRepository.findByUsername("testUsername"));
        assertTrue(setRepository.findAll().isEmpty());

        // card List
        List<Card> emptyList = new ArrayList<>();
        // user setup
        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        User createdUser = userService.createUser(testUser);
        // set setup
        Set testSet = new Set();
        testSet.setUser(createdUser);
        testSet.setTitle("title");
        testSet.setCards(emptyList);
        testSet.setSetCategory(SetCategory.BIOLOGY);
        testSet.setSetStatus(SetStatus.PUBLIC);
        testSet.setExplain("explain");
        testSet.setPhoto("photo");

        // when
        Set createdSet = setService.createSet(testSet);
        assertTrue(setRepository.findBySetId(createdSet.getSetId()).isPresent());
        setService.deleteSet(createdSet.getSetId());

        // assert no instances of set in repo
        assertTrue(setRepository.findAll().isEmpty());
    }

    @Test
    public void checkDeleteSet_wrongSetId_Throw(){
        // given
        assertNull(userRepository.findByUsername("testUsername"));
        assertTrue(setRepository.findAll().isEmpty());

        // card List
        List<Card> emptyList = new ArrayList<>();
        // user setup
        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        User createdUser = userService.createUser(testUser);
        // set setup
        Set testSet = new Set();
        testSet.setUser(createdUser);
        testSet.setTitle("title");
        testSet.setCards(emptyList);
        testSet.setSetCategory(SetCategory.BIOLOGY);
        testSet.setSetStatus(SetStatus.PUBLIC);
        testSet.setExplain("explain");
        testSet.setPhoto("photo");

        // when
        Set createdSet = setService.createSet(testSet);
        assertTrue(setRepository.findBySetId(createdSet.getSetId()).isPresent());

        // Assert throw
        assertThrows(ResponseStatusException.class,() -> setService.deleteSet(2L));
    }
}
