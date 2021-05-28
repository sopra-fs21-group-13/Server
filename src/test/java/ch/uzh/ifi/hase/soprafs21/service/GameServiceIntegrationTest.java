package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.GameStatus;
import ch.uzh.ifi.hase.soprafs21.constant.SetCategory;
import ch.uzh.ifi.hase.soprafs21.constant.SetStatus;
import ch.uzh.ifi.hase.soprafs21.entity.*;
import ch.uzh.ifi.hase.soprafs21.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the GameResource REST resource.
 *
 * @see GameService
 */
@WebAppConfiguration
@SpringBootTest
public class GameServiceIntegrationTest {

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

    @Qualifier("gameRepository")
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameService gameService;

    @Qualifier("invitationRepository")
    @Autowired
    private InvitationRepository invitationRepository;

    @BeforeEach
    public void setup() {
        invitationRepository.deleteAll();
        gameRepository.deleteAll();
        settingsRepository.deleteAll();
        setRepository.deleteAll();
        userRepository.deleteAll();

    }

    @AfterEach
    public void tearDown() {
        invitationRepository.deleteAll();
        gameRepository.deleteAll();
        settingsRepository.deleteAll();
        setRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void createGame_validInputs_success() {
        // given
        assertNull(userRepository.findByUsername("testUsername"));
        assertTrue(setRepository.findAll().isEmpty());
        assertTrue(gameRepository.findAll().isEmpty());
        // cardList
        List<Card> cardList = new ArrayList<>();
        Card card = new Card();
        card.setQuestion("what?");
        card.setAnswer("Because!");
        cardList.add(card);
        // user setup
        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        testUser = userService.createUser(testUser);
        // set setup
        Set testSet = new Set();
        testSet.setTitle("title");
        testSet.setUser(testUser);
        testSet.setCards(cardList);
        testSet.setSetCategory(SetCategory.BIOLOGY);
        testSet.setSetStatus(SetStatus.PUBLIC);
        testSet.setExplain("explain");
        testSet.setPhoto("photo");
        Set createdSet = setService.createSet(testSet);
        // given
        GameSetting gameSetting = new GameSetting();
        gameSetting.setGameSettingId(1L);
        gameSetting.setTime(100L);
        gameSetting.setNumberOfCards(10L);
        gameSetting.setNumberOfPlayers(2L);
        // game setup
        Game testgame = new Game();
        testgame.setGameSettings(gameSetting);
        testgame.setInviter(testUser);
        testgame.setPlaySetId(1L);
        testgame.setCountDown(false);
        testgame.setTimer(10L);

        //when
        Game createdGame = gameService.createGame(testgame);

        // then
        assertEquals(createdGame.getPlaySetId(), testgame.getPlaySetId());
        assertEquals(createdGame.getStatus(), testgame.getStatus());
        assertEquals(createdGame.getGameSettings(), testgame.getGameSettings());
        assertNotNull(createdGame.getGameId());
        assertEquals(createdGame.getInviter(), testgame.getInviter());
        assertEquals(createdGame.getPlayers(), testgame.getPlayers());
        assertEquals(createdGame.getCountDown(), testgame.getCountDown());
        assertEquals(createdGame.getTimer(), testgame.getTimer());
    }

    @Test
    public void createGame_emptyGame_throwsException() {
        //given
        assertTrue(gameRepository.findAll().isEmpty());

        // user setup
        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        // game setup
        Game testgame = new Game();

        // check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> gameService.createGame(testgame));
    }

    @Test
    public void createGame_NoInviter_throwsException() {
        //given
        assertTrue(setRepository.findAll().isEmpty());
        // card List
        List<Card> emptyList = new ArrayList<>();
        // user setup
        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        // gameSetting setup
        GameSetting gameSetting = new GameSetting();
        gameSetting.setGameSettingId(1L);
        gameSetting.setTime(100L);
        gameSetting.setNumberOfCards(10L);
        gameSetting.setNumberOfPlayers(2L);
        // game setup
        Game testgame = new Game();
        testgame.setGameSettings(gameSetting);
        testgame.setPlaySetId(1L);
        testgame.setCountDown(false);
        testgame.setTimer(10L);

        // check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> gameService.createGame(testgame));
    }

    @Test
    public void checkUpdateGame_ValidInput_success() {
        // given
        assertNull(userRepository.findByUsername("testUsername"));
        assertTrue(setRepository.findAll().isEmpty());
        assertTrue(gameRepository.findAll().isEmpty());
        // cardList
        List<Card> emptyList = new ArrayList<>();
        // user setup
        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        User createdUser = userService.createUser(testUser);
        // cardList
        List<Card> cardList = new ArrayList<>();
        Card card = new Card();
        card.setQuestion("what?");
        card.setAnswer("Because!");
        cardList.add(card);
        // set setup
        Set testSet = new Set();
        testSet.setTitle("title");
        testSet.setUser(testUser);
        testSet.setCards(cardList);
        testSet.setSetCategory(SetCategory.BIOLOGY);
        testSet.setSetStatus(SetStatus.PUBLIC);
        testSet.setExplain("explain");
        testSet.setPhoto("photo");
        Set createdSet = setService.createSet(testSet);
        // gameSetting setup
        GameSetting gameSetting = new GameSetting();
        gameSetting.setGameSettingId(1L);
        gameSetting.setTime(100L);
        gameSetting.setNumberOfCards(10L);
        gameSetting.setNumberOfPlayers(2L);
        // game setup
        Game testgame = new Game();
        testgame.setGameSettings(gameSetting);
        testgame.setInviter(testUser);
        testgame.setPlaySetId(1L);
        testgame.setCountDown(false);
        testgame.setTimer(10L);
        Game testGame = gameService.createGame(testgame);

        // game setup
        Game testGame2 = new Game();
        testGame2.setGameId(testGame.getGameId());
        testGame2.setCountDown(true);
        testGame2.setTimer(20L);

        // when
        Game updatedGame = gameService.updateGame(testGame2);

        // then
        assertEquals(updatedGame.getPlaySetId(), testGame.getPlaySetId());
        assertEquals(updatedGame.getStatus(), testGame.getStatus());
        assertEquals(updatedGame.getGameSettings().getGameSettingId(), testGame.getGameSettings().getGameSettingId());
        assertNotNull(updatedGame.getGameId());
        assertEquals(updatedGame.getInviter().getUserId(), testGame.getInviter().getUserId());
        assertEquals(updatedGame.getCountDown(), testGame2.getCountDown());
        assertEquals(updatedGame.getTimer(), testGame2.getTimer());

    }


    @Test
    public void checkAddMessageToHistory_ValidInput_success(){
        // given
        assertNull(userRepository.findByUsername("testUsername"));
        assertTrue(setRepository.findAll().isEmpty());
        assertTrue(gameRepository.findAll().isEmpty());
        // cardList
        List<Card> emptyList = new ArrayList<>();
        // user setup
        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        User createdUser = userService.createUser(testUser);
        // cardList
        List<Card> cardList = new ArrayList<>();
        Card card = new Card();
        card.setQuestion("what?");
        card.setAnswer("Because!");
        cardList.add(card);
        // set setup
        Set testSet = new Set();
        testSet.setTitle("title");
        testSet.setUser(testUser);
        testSet.setCards(cardList);
        testSet.setSetCategory(SetCategory.BIOLOGY);
        testSet.setSetStatus(SetStatus.PUBLIC);
        testSet.setExplain("explain");
        testSet.setPhoto("photo");
        Set createdSet = setService.createSet(testSet);
        // gameSetting setup
        GameSetting gameSetting = new GameSetting();
        gameSetting.setGameSettingId(1L);
        gameSetting.setTime(100L);
        gameSetting.setNumberOfCards(10L);
        gameSetting.setNumberOfPlayers(2L);
        // game setup
        Game testgame = new Game();
        testgame.setGameSettings(gameSetting);
        testgame.setInviter(testUser);
        testgame.setPlaySetId(1L);
        testgame.setCountDown(false);
        testgame.setTimer(10L);
        Game testGame = gameService.createGame(testgame);

        // message Setup
        Message message = new Message();
        message.setTimeStamp(LocalDateTime.now());
        message.setSenderId(testUser.getUserId());
        message.setMessage("message");
        message.setCardId(1L);
        message.setScore(1L);

        Game updatedGame = gameService.addMessageToHistory(testGame.getGameId(), message);

        assertEquals(updatedGame.getHistory().get(0), message);
    }

    @Test
    public void checkAddPlayerToGame_ValidInput_success(){
        // given
        assertNull(userRepository.findByUsername("testUsername"));
        assertTrue(setRepository.findAll().isEmpty());
        assertTrue(gameRepository.findAll().isEmpty());
        // cardList
        List<Card> emptyList = new ArrayList<>();
        // user setup
        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        User createdUser = userService.createUser(testUser);
        // cardList
        List<Card> cardList = new ArrayList<>();
        Card card = new Card();
        card.setQuestion("what?");
        card.setAnswer("Because!");
        cardList.add(card);
        // set setup
        Set testSet = new Set();
        testSet.setTitle("title");
        testSet.setUser(testUser);
        testSet.setCards(cardList);
        testSet.setSetCategory(SetCategory.BIOLOGY);
        testSet.setSetStatus(SetStatus.PUBLIC);
        testSet.setExplain("explain");
        testSet.setPhoto("photo");
        Set createdSet = setService.createSet(testSet);
        // gameSetting setup
        GameSetting gameSetting = new GameSetting();
        gameSetting.setGameSettingId(1L);
        gameSetting.setTime(100L);
        gameSetting.setNumberOfCards(10L);
        gameSetting.setNumberOfPlayers(2L);
        // game setup
        Game testgame = new Game();
        testgame.setGameSettings(gameSetting);
        testgame.setInviter(testUser);
        testgame.setPlaySetId(1L);
        testgame.setCountDown(false);
        testgame.setTimer(10L);
        Game testGame = gameService.createGame(testgame);
        // testAddUser setup
        // user setup
        User testUser2 = new User();
        testUser2.setName("testName2");
        testUser2.setUsername("testUsername2");
        testUser2.setPassword("password2");
        User createdUser2 = userService.createUser(testUser2);


        Game updatedGame = gameService.addPlayerToGame(testGame.getGameId(), createdUser2.getUserId());

        assertEquals(updatedGame.getPlayers().get(1).getUserId(), createdUser2.getUserId() );
    }

    @Test
    public void checkRemovePlayerToGame_ValidInput_success(){
        // given
        assertNull(userRepository.findByUsername("testUsername"));
        assertTrue(setRepository.findAll().isEmpty());
        assertTrue(gameRepository.findAll().isEmpty());
        // cardList
        List<Card> emptyList = new ArrayList<>();
        // user setup
        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        User createdUser = userService.createUser(testUser);
        // cardList
        List<Card> cardList = new ArrayList<>();
        Card card = new Card();
        card.setQuestion("what?");
        card.setAnswer("Because!");
        cardList.add(card);
        // set setup
        Set testSet = new Set();
        testSet.setTitle("title");
        testSet.setUser(testUser);
        testSet.setCards(cardList);
        testSet.setSetCategory(SetCategory.BIOLOGY);
        testSet.setSetStatus(SetStatus.PUBLIC);
        testSet.setExplain("explain");
        testSet.setPhoto("photo");
        Set createdSet = setService.createSet(testSet);
        // gameSetting setup
        GameSetting gameSetting = new GameSetting();
        gameSetting.setGameSettingId(1L);
        gameSetting.setTime(100L);
        gameSetting.setNumberOfCards(10L);
        gameSetting.setNumberOfPlayers(2L);
        // game setup
        Game testgame = new Game();
        testgame.setGameSettings(gameSetting);
        testgame.setInviter(testUser);
        testgame.setPlaySetId(1L);
        testgame.setCountDown(false);
        testgame.setTimer(10L);
        Game testGame = gameService.createGame(testgame);
        // testAddUser setup
        // user setup
        User testUser2 = new User();
        testUser2.setName("testName2");
        testUser2.setUsername("testUsername2");
        testUser2.setPassword("password2");
        User createdUser2 = userService.createUser(testUser2);


        Game updatedGame = gameService.addPlayerToGame(testGame.getGameId(), createdUser2.getUserId());

        Game removedPlayerGame = gameService.removePlayerFromGame(updatedGame.getGameId(),createdUser2.getUserId());

        assertEquals(removedPlayerGame.getPlayers().size(), testGame.getPlayers().size());
    }

    @Test
    public void checkCreateInvitation_ValidInput_success(){
        // given
        assertNull(userRepository.findByUsername("testUsername"));
        assertTrue(setRepository.findAll().isEmpty());
        assertTrue(gameRepository.findAll().isEmpty());
        // cardList
        List<Card> emptyList = new ArrayList<>();
        // user setup
        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        User createdUser = userService.createUser(testUser);
        // user setup
        User testUser2 = new User();
        testUser2.setName("testName2");
        testUser2.setUsername("testUsername2");
        testUser2.setPassword("password2");
        User createdUser2 = userService.createUser(testUser2);
        // cardList
        List<Card> cardList = new ArrayList<>();
        Card card = new Card();
        card.setQuestion("what?");
        card.setAnswer("Because!");
        cardList.add(card);
        // set setup
        Set testSet = new Set();
        testSet.setTitle("title");
        testSet.setUser(testUser);
        testSet.setCards(cardList);
        testSet.setSetCategory(SetCategory.BIOLOGY);
        testSet.setSetStatus(SetStatus.PUBLIC);
        testSet.setExplain("explain");
        testSet.setPhoto("photo");
        Set createdSet = setService.createSet(testSet);
        // gameSetting setup
        GameSetting gameSetting = new GameSetting();
        gameSetting.setGameSettingId(1L);
        gameSetting.setTime(100L);
        gameSetting.setNumberOfCards(10L);
        gameSetting.setNumberOfPlayers(2L);
        // game setup
        Game testgame = new Game();
        testgame.setGameSettings(gameSetting);
        testgame.setInviter(testUser);
        testgame.setPlaySetId(1L);
        testgame.setCountDown(false);
        testgame.setTimer(10L);
        Game testGame = gameService.createGame(testgame);
        // Invitation setup
        Invitation invitation = new Invitation();
        invitation.setGameId(testGame.getGameId());
        invitation.setSentFromId(testUser.getUserId());
        invitation.setSentFromUserName("user");
        invitation.setReceivers(Collections.singletonList(testUser2));
        invitation.setSetTitle("title");
        invitation.setGameSetting(testGame.getGameSettings());

        Invitation createdInvitation = gameService.createInvitation(invitation);

        // then
        assertEquals(createdInvitation.getGameId(), invitation.getGameId());
        assertEquals(createdInvitation.getSentFromId(), invitation.getSentFromId());
        assertEquals(createdInvitation.getSentFromUserName(), invitation.getSentFromUserName());
        assertNotNull(createdInvitation.getInvitationId());
        assertEquals(createdInvitation.getReceivers(), invitation.getReceivers());
        assertEquals(createdInvitation.getSetTitle(), invitation.getSetTitle());
        assertEquals(createdInvitation.getGameSetting(), invitation.getGameSetting());

    }

    @Test
    public void deleteGame_validInputs_success() {
        // given
        assertNull(userRepository.findByUsername("testUsername"));
        assertTrue(setRepository.findAll().isEmpty());
        assertTrue(gameRepository.findAll().isEmpty());
        // cardList
        List<Card> cardList = new ArrayList<>();
        Card card = new Card();
        card.setQuestion("what?");
        card.setAnswer("Because!");
        cardList.add(card);
        // user setup
        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        testUser = userService.createUser(testUser);
        // set setup
        Set testSet = new Set();
        testSet.setTitle("title");
        testSet.setUser(testUser);
        testSet.setCards(cardList);
        testSet.setSetCategory(SetCategory.BIOLOGY);
        testSet.setSetStatus(SetStatus.PUBLIC);
        testSet.setExplain("explain");
        testSet.setPhoto("photo");
        Set createdSet = setService.createSet(testSet);
        // given
        GameSetting gameSetting = new GameSetting();
        gameSetting.setGameSettingId(1L);
        gameSetting.setTime(100L);
        gameSetting.setNumberOfCards(10L);
        gameSetting.setNumberOfPlayers(2L);
        // game setup
        Game testgame = new Game();
        testgame.setGameSettings(gameSetting);
        testgame.setInviter(testUser);
        testgame.setPlaySetId(testSet.getSetId());
        testgame.setCountDown(false);
        testgame.setTimer(10L);

        //when
        Game createdGame = gameService.createGame(testgame);
        assertTrue(gameRepository.findByGameId(createdGame.getGameId()).isPresent());
        gameService.deleteGame(createdGame.getGameId());

        // then
        assertTrue(gameRepository.findAll().isEmpty());
    }

    @Test
    public void checkDeleteInvitation_ValidInput_success(){
        // given
        assertNull(userRepository.findByUsername("testUsername"));
        assertTrue(setRepository.findAll().isEmpty());
        assertTrue(gameRepository.findAll().isEmpty());
        // cardList
        List<Card> emptyList = new ArrayList<>();
        // user setup
        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        User createdUser = userService.createUser(testUser);
        // user setup
        User testUser2 = new User();
        testUser2.setName("testName2");
        testUser2.setUsername("testUsername2");
        testUser2.setPassword("password2");
        User createdUser2 = userService.createUser(testUser2);
        // cardList
        List<Card> cardList = new ArrayList<>();
        Card card = new Card();
        card.setQuestion("what?");
        card.setAnswer("Because!");
        cardList.add(card);
        // set setup
        Set testSet = new Set();
        testSet.setTitle("title");
        testSet.setUser(testUser);
        testSet.setCards(cardList);
        testSet.setSetCategory(SetCategory.BIOLOGY);
        testSet.setSetStatus(SetStatus.PUBLIC);
        testSet.setExplain("explain");
        testSet.setPhoto("photo");
        Set createdSet = setService.createSet(testSet);
        // gameSetting setup
        GameSetting gameSetting = new GameSetting();
        gameSetting.setGameSettingId(1L);
        gameSetting.setTime(100L);
        gameSetting.setNumberOfCards(10L);
        gameSetting.setNumberOfPlayers(2L);
        // game setup
        Game testgame = new Game();
        testgame.setGameSettings(gameSetting);
        testgame.setInviter(testUser);
        testgame.setPlaySetId(1L);
        testgame.setCountDown(false);
        testgame.setTimer(10L);
        Game testGame = gameService.createGame(testgame);
        // Invitation setup
        Invitation invitation = new Invitation();
        invitation.setGameId(1L);
        invitation.setSentFromId(1L);
        invitation.setSentFromUserName("user");
        invitation.setReceivers(Collections.singletonList(testUser2));
        invitation.setSetTitle("title");
        invitation.setGameSetting(testGame.getGameSettings());

        //when
        Invitation createdInvitation = gameService.createInvitation(invitation);
        assertTrue(invitationRepository.findByInvitationId(createdInvitation.getGameId()).isPresent());
        gameService.deleteInvitation(createdInvitation.getInvitationId());
        // then
        assertTrue(invitationRepository.findAll().isEmpty());

    }
}
