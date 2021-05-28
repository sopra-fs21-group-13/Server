package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.constant.GameStatus;
import ch.uzh.ifi.hase.soprafs21.constant.SetCategory;
import ch.uzh.ifi.hase.soprafs21.constant.SetStatus;
import ch.uzh.ifi.hase.soprafs21.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs21.entity.*;
import ch.uzh.ifi.hase.soprafs21.rest.dto.GamePostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.InvitationPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.MessagePostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.SetPostDTO;
import ch.uzh.ifi.hase.soprafs21.service.GameService;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private UserService userService;


    @Test
    public void givenGames_whenGetGames_thenReturnJsonArray() throws Exception {

        // given
        GameSetting gameSetting = new GameSetting();
        gameSetting.setGameSettingId(1L);
        gameSetting.setTime(100L);
        gameSetting.setNumberOfCards(10L);
        gameSetting.setNumberOfPlayers(2L);

        Game game = new Game();
        game.setGameId(1L);
        game.setStatus(GameStatus.OPEN);
        game.setGameSettings(gameSetting);
        game.setInviter(new User());
        game.setPlaySetId(1L);
        game.setPlayCards(Collections.singletonList(new Card()));
        game.setPlayers(Collections.singletonList(new User()));
        game.setCountDown(false);
        game.setTimer(10L);
        game.setHistory(Collections.singletonList(new Message()));



        List<Game> allGames = Collections.singletonList(game);

        // this mocks the UserService -> we define above what the userService should return when getUsers() is called
        given(gameService.getAllGames()).willReturn(allGames);


        // when
        MockHttpServletRequestBuilder getRequest = get("/games").contentType(MediaType.APPLICATION_JSON);



        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].gameId", is(1)))
                .andExpect(jsonPath("$[0].gameSettings.gameSettingId", is(game.getGameSettings().getGameSettingId().intValue())))
                .andExpect(jsonPath("$[0].status", is(game.getStatus().toString())))
                .andExpect(jsonPath("$[0].inviter.userId", is(game.getInviter().getUserId())))
                .andExpect(jsonPath("$[0].playSetId", is(game.getPlaySetId().intValue())))
                .andExpect(jsonPath("$[0].playCards[0].cardId", is(game.getPlayCards().get(0).getCardId())))
                .andExpect(jsonPath("$[0].players[0].userId", is(game.getPlayers().get(0).getUserId())))
                .andExpect(jsonPath("$[0].countDown", is(game.getCountDown())))
                .andExpect(jsonPath("$[0].timer", is(game.getTimer().intValue())))
                .andExpect(jsonPath("$[0].history[0].messageId", is(game.getHistory().get(0).getMessageId())));
    }

    @Test
    public void givenGame_whenGetGameById_thenReturnJsonArray() throws Exception {

        // given
        GameSetting gameSetting = new GameSetting();
        gameSetting.setGameSettingId(1L);
        gameSetting.setTime(100L);
        gameSetting.setNumberOfCards(10L);
        gameSetting.setNumberOfPlayers(2L);

        Game game = new Game();
        game.setGameId(1L);
        game.setStatus(GameStatus.OPEN);
        game.setGameSettings(gameSetting);
        game.setInviter(new User());
        game.setPlaySetId(1L);
        game.setPlayCards(Collections.singletonList(new Card()));
        game.setPlayers(Collections.singletonList(new User()));
        game.setCountDown(false);
        game.setTimer(10L);
        game.setHistory(Collections.singletonList(new Message()));

        // this mocks the UserService -> we define above what the userService should return when getUsers() is called
        given(gameService.getGameByGameID(Mockito.eq(1L))).willReturn(game);


        // when
        MockHttpServletRequestBuilder getRequest = get("/games/1").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isAccepted())
                .andExpect(jsonPath("$.*", hasSize(10)))
                .andExpect(jsonPath("$.gameId", is(1)))
                .andExpect(jsonPath("$.gameSettings.gameSettingId", is(game.getGameSettings().getGameSettingId().intValue())))
                .andExpect(jsonPath("$.status", is(game.getStatus().toString())))
                .andExpect(jsonPath("$.inviter.userId", is(game.getInviter().getUserId())))
                .andExpect(jsonPath("$.playSetId", is(game.getPlaySetId().intValue())))
                .andExpect(jsonPath("$.playCards[0].cardId", is(game.getPlayCards().get(0).getCardId())))
                .andExpect(jsonPath("$.players[0].userId", is(game.getPlayers().get(0).getUserId())))
                .andExpect(jsonPath("$.countDown", is(game.getCountDown())))
                .andExpect(jsonPath("$.timer", is(game.getTimer().intValue())))
                .andExpect(jsonPath("$.history[0].messageId", is(game.getHistory().get(0).getMessageId())));

    }

    @Test
    public void createGame_validInput_setGame() throws Exception {

        // given
        GameSetting gameSetting = new GameSetting();
        gameSetting.setGameSettingId(1L);
        gameSetting.setTime(100L);
        gameSetting.setNumberOfCards(10L);
        gameSetting.setNumberOfPlayers(2L);

        Game game = new Game();
        game.setGameId(1L);
        game.setStatus(GameStatus.OPEN);
        game.setGameSettings(gameSetting);
        game.setInviter(new User());
        game.setPlaySetId(1L);
        game.setPlayCards(Collections.singletonList(new Card()));
        game.setPlayers(Collections.singletonList(new User()));
        game.setCountDown(false);
        game.setTimer(10L);
        game.setHistory(Collections.singletonList(new Message()));

        GamePostDTO gamePostDTO = new GamePostDTO();
        gamePostDTO.setStatus(GameStatus.OPEN);
        gamePostDTO.setGameSettings(gameSetting);
        gamePostDTO.setInviter(new User());
        gamePostDTO.setPlaySetId(1L);
        gamePostDTO.setPlayCards(Collections.singletonList(new Card()));
        gamePostDTO.setCountDown(false);
        gamePostDTO.setTimer(10L);
        gamePostDTO.setHistory(Collections.singletonList(new Message()));

        given(gameService.createGame(Mockito.any())).willReturn(game);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/games")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(gamePostDTO));

        // then
        mockMvc.perform(postRequest).andExpect(status().isCreated())
                .andExpect(jsonPath("$.*", hasSize(10)))
                .andExpect(jsonPath("$.gameId", is(1)))
                .andExpect(jsonPath("$.gameSettings.gameSettingId", is(game.getGameSettings().getGameSettingId().intValue())))
                .andExpect(jsonPath("$.status", is(game.getStatus().toString())))
                .andExpect(jsonPath("$.inviter.userId", is(game.getInviter().getUserId())))
                .andExpect(jsonPath("$.playSetId", is(game.getPlaySetId().intValue())))
                .andExpect(jsonPath("$.playCards[0].cardId", is(game.getPlayCards().get(0).getCardId())))
                .andExpect(jsonPath("$.countDown", is(game.getCountDown())))
                .andExpect(jsonPath("$.timer", is(game.getTimer().intValue())))
                .andExpect(jsonPath("$.history[0].messageId", is(game.getHistory().get(0).getMessageId())));
    }

    @Test
    public void createInvitation_validInput_setInvitation() throws Exception {

        // given
        GameSetting gameSetting = new GameSetting();
        gameSetting.setGameSettingId(1L);
        gameSetting.setTime(100L);
        gameSetting.setNumberOfCards(10L);
        gameSetting.setNumberOfPlayers(2L);

        Invitation invitation = new Invitation();
        invitation.setInvitationId(1L);
        invitation.setGameId(1L);
        invitation.setSentFromId(1L);
        invitation.setSentFromUserName("user");
        invitation.setReceivers(Collections.singletonList(new User()));
        invitation.setSetTitle("title");
        invitation.setGameSetting(gameSetting);

        InvitationPostDTO invitationPostDTO = new InvitationPostDTO();
        invitationPostDTO.setGameId(1L);
        invitationPostDTO.setSentFromId(1L);
        invitationPostDTO.setSentFromUserName("user");
        invitationPostDTO.setReceivers(Collections.singletonList(new User()));
        invitationPostDTO.setSetTitle("title");
        invitationPostDTO.setGameSetting(gameSetting);


        given(gameService.createInvitation(Mockito.any())).willReturn(invitation);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/games/invitations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(invitationPostDTO));

        // then
        mockMvc.perform(postRequest).andExpect(status().isCreated())
                .andExpect(jsonPath("$.*", hasSize(7)))
                .andExpect(jsonPath("$.gameId", is(1)))
                .andExpect(jsonPath("$.sentFromId", is(invitation.getSentFromId().intValue())))
                .andExpect(jsonPath("$.sentFromUserName", is(invitation.getSentFromUserName())))
                .andExpect(jsonPath("$.receivers", is(invitation.getReceivers())))
                .andExpect(jsonPath("$.setTitle", is(invitation.getSetTitle())))
                .andExpect(jsonPath("$.gameSetting.gameSettingId", is(invitation.getGameSetting().getGameSettingId().intValue())));
    }




    @Test
    public void givenGame_updateGame_validInput() throws Exception {

        // given
        GameSetting gameSetting = new GameSetting();
        gameSetting.setGameSettingId(1L);
        gameSetting.setTime(100L);
        gameSetting.setNumberOfCards(10L);
        gameSetting.setNumberOfPlayers(2L);

        Game game = new Game();
        game.setGameId(1L);
        game.setStatus(GameStatus.OPEN);
        game.setGameSettings(gameSetting);
        game.setInviter(new User());
        game.setPlaySetId(1L);
        game.setPlayCards(Collections.singletonList(new Card()));
        game.setPlayers(Collections.singletonList(new User()));
        game.setCountDown(false);
        game.setTimer(10L);
        game.setHistory(Collections.singletonList(new Message()));

        GamePostDTO gamePostDTO = new GamePostDTO();
        gamePostDTO.setGameId(1L);
        gamePostDTO.setStatus(GameStatus.CLOSED);
        gamePostDTO.setPlaySetId(1L);
        gamePostDTO.setPlayCards(Collections.singletonList(new Card()));
        gamePostDTO.setCountDown(true);
        gamePostDTO.setTimer(5L );
        gamePostDTO.setHistory(Collections.singletonList(new Message()));

        given(gameService.updateGame(Mockito.any())).willReturn(game);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder putRequest = put("/games")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(gamePostDTO));

        // then
        mockMvc.perform(putRequest).andExpect(status().isAccepted())
                .andExpect(jsonPath("$.*", hasSize(10)))
                .andExpect(jsonPath("$.gameId", is(1)))
                .andExpect(jsonPath("$.gameSettings.gameSettingId", is(game.getGameSettings().getGameSettingId().intValue())))
                .andExpect(jsonPath("$.status", is(game.getStatus().toString())))
                .andExpect(jsonPath("$.inviter.userId", is(game.getInviter().getUserId())))
                .andExpect(jsonPath("$.playSetId", is(game.getPlaySetId().intValue())))
                .andExpect(jsonPath("$.playCards[0].cardId", is(game.getPlayCards().get(0).getCardId())))
                .andExpect(jsonPath("$.countDown", is(game.getCountDown())))
                .andExpect(jsonPath("$.timer", is(game.getTimer().intValue())))
                .andExpect(jsonPath("$.history[0].messageId", is(game.getHistory().get(0).getMessageId())));
    }

    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("The request body could not be created.%s", e.toString()));
        }
    }


}
