package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.GameStatus;
import ch.uzh.ifi.hase.soprafs21.constant.SetCategory;
import ch.uzh.ifi.hase.soprafs21.constant.SetStatus;
import ch.uzh.ifi.hase.soprafs21.entity.*;
import ch.uzh.ifi.hase.soprafs21.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

public class GameServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SetRepository setRepository;

    @Mock
    private SettingsRepository settingsRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameSettingRepository gameSettingRepository;

    @Mock
    private InvitationRepository invitationRepository;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private GameService gameService;

    private Game testGame;

    private GameSetting gameSetting;

    private Invitation testInvitation;

    private User testUser;

    private Message testMessage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // given
        testUser = new User();
        testUser.setUserId(1L);
        testUser.setUsername("testUser");
        testUser.setPassword("password");

        gameSetting = new GameSetting();
        gameSetting.setGameSettingId(1L);
        gameSetting.setTime(100L);
        gameSetting.setNumberOfCards(10L);
        gameSetting.setNumberOfPlayers(2L);

        testGame = new Game();
        testGame.setGameId(1L);
        testGame.setStatus(GameStatus.OPEN);
        testGame.setGameSettings(gameSetting);
        testGame.setInviter(testUser);
        testGame.setPlaySetId(1L);
        testGame.setPlayCards(Collections.singletonList(new Card()));
        testGame.setPlayers(Collections.singletonList(testUser));
        testGame.setCountDown(false);
        testGame.setTimer(10L);
        testGame.setHistory(Collections.singletonList(new Message()));

        testInvitation = new Invitation();
        testInvitation.setGameId(1L);
        testInvitation.setSentFromId(1L);
        testInvitation.setSentFromUserName("user");
        testInvitation.setReceivers(Collections.singletonList(new User()));
        testInvitation.setSetTitle("title");
        testInvitation.setGameSetting(testGame.getGameSettings());


        // when -> any object is being saved in the gameRepository -> return the dummy testGame
        Mockito.when(gameSettingRepository.save(Mockito.any())).thenReturn(gameSetting);
        Mockito.when(gameRepository.save(Mockito.any())).thenReturn(testGame);



    }

    @Test
    public void getPublicSets_validInputs() {

        gameService.getAllGames();

        verify(gameRepository).findAll();
    }

    @Test
    public void getGameByGameId_validInputs_success() {

        Mockito.when(gameRepository.findByGameId(Mockito.any())).thenReturn(Optional.of(testGame));

        Game createdGame = gameService.getGameByGameID(testGame.getGameId());

        assertEquals(createdGame.getPlaySetId(), testGame.getPlaySetId());
        assertEquals(createdGame.getStatus(), testGame.getStatus());
        assertEquals(createdGame.getGameSettings(), testGame.getGameSettings());
        assertNotNull(createdGame.getGameId());
        assertEquals(createdGame.getInviter(), testGame.getInviter());
        assertEquals(createdGame.getPlayers(), testGame.getPlayers());
        assertEquals(createdGame.getCountDown(), testGame.getCountDown());
        assertEquals(createdGame.getTimer(), testGame.getTimer());
    }

    @Test
    public void getGameId_InvalidInputs_UnSuccess() {

        Mockito.when(gameRepository.findByGameId(Mockito.any())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> gameService.getGameByGameID(testGame.getGameId()));

        Exception e = assertThrows(ResponseStatusException.class, () -> gameService.getGameByGameID(testGame.getGameId()));
        assertEquals("400 BAD_REQUEST \"Ain't no game with gameId\"", e.getMessage());
    }



    @Test
    public void createGame_validInputs_success() {

        Game createdGame = gameService.createGame(testGame);

        verify(gameRepository, Mockito.times(1)).save(Mockito.any());

        assertEquals(createdGame.getPlaySetId(), testGame.getPlaySetId());
        assertEquals(createdGame.getStatus(), testGame.getStatus());
        assertEquals(createdGame.getGameSettings(), testGame.getGameSettings());
        assertNotNull(createdGame.getGameId());
        assertEquals(createdGame.getInviter(), testGame.getInviter());
        assertEquals(createdGame.getPlayers(), testGame.getPlayers());
        assertEquals(createdGame.getCountDown(), testGame.getCountDown());
        assertEquals(createdGame.getTimer(), testGame.getTimer());

    }


    @Test
    public void createGame_InvalidInputs_Fail() {

        Game newGame = new Game();

        assertThrows(ResponseStatusException.class, () -> gameService.createGame(newGame));

        Exception e = assertThrows(ResponseStatusException.class, () -> gameService.createGame(newGame));
        assertEquals("400 BAD_REQUEST \"Game has no Inviter\"", e.getMessage());
    }


    @Test
    public void checkGame_success() {

        boolean Boolean = gameService.checkGame(testGame);

        assertFalse(Boolean);
    }


    @Test
    public void checkGame_InvalidInputs_Fail() {

        Game game = new Game();

        assertThrows(ResponseStatusException.class, () -> gameService.checkGame(game));

        Exception e = assertThrows(ResponseStatusException.class, () -> gameService.checkGame(game));
        assertEquals("400 BAD_REQUEST \"Game has no Inviter\"", e.getMessage());

    }

    @Test
    public void updateGame_Success() {

        // game setup
        Game updateGame = new Game();
        updateGame.setGameId(testGame.getGameId());
        updateGame.setCountDown(true);
        updateGame.setTimer(20L);

        Mockito.when(gameRepository.findByGameId(Mockito.any())).thenReturn(Optional.of(testGame));

        updateGame = gameService.updateGame(updateGame);

        // then
        assertEquals(updateGame.getPlaySetId(), testGame.getPlaySetId());
        assertEquals(updateGame.getStatus(), testGame.getStatus());
        assertEquals(updateGame.getGameSettings().getGameSettingId(), testGame.getGameSettings().getGameSettingId());
        assertNotNull(updateGame.getGameId());
        assertEquals(updateGame.getInviter().getUserId(), testGame.getInviter().getUserId());
        assertEquals(updateGame.getCountDown(), testGame.getCountDown());
        assertEquals(updateGame.getTimer(), testGame.getTimer());

    }

    @Test
    public void updateGame_InvalidInput_Fail() {

        Mockito.when(gameRepository.findByGameId(Mockito.any())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> gameService.updateGame(testGame));

        Exception e = assertThrows(ResponseStatusException.class, () -> gameService.updateGame(testGame));
        assertEquals("400 BAD_REQUEST \"Ain't no game with gameId\"", e.getMessage());

    }

    /*
    @Test
    public void addMessageToHistory_validInputs_success() {

        Mockito.when(gameRepository.findByGameId(Mockito.any())).thenReturn(Optional.of(testGame));
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(testUser));

        testMessage = new Message();
        testMessage.setTimeStamp(LocalDateTime.now());
        testMessage.setSenderId(testGame.getPlayers().get(0).getUserId());
        testMessage.setMessage("message");
        testMessage.setCardId(1L);
        testMessage.setScore(1L);

        Mockito.when(messageRepository.findByMessageId(Mockito.any())).thenReturn(Optional.of(testMessage));



        Game MessageAddedGame = gameService.addMessageToHistory(testGame.getGameId(), testMessage);

        assertEquals(MessageAddedGame.getHistory().get(0), testMessage);
    }
    
     */


    @Test
    public void deleteSet_Success() {

        Mockito.when(gameRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.when(gameRepository.findByGameId(Mockito.any())).thenReturn(Optional.of(testGame));
        Mockito.when(invitationRepository.findByInvitationId(Mockito.any())).thenReturn(Optional.of(testInvitation));

        gameService.deleteGame(testGame.getGameId());

        verify(gameRepository).deleteById(Mockito.any());
    }

    @Test
    public void deleteSet_InvalidInput_Fail() {

        Mockito.when(gameRepository.existsById(Mockito.any())).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> gameService.deleteGame(testGame.getGameId()));

        Exception e = assertThrows(ResponseStatusException.class, () -> gameService.deleteGame(testGame.getGameId()));
        assertEquals("400 BAD_REQUEST \"Ain't no game with gameId\"", e.getMessage());

    }


}
