package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.constant.GameStatus;
import ch.uzh.ifi.hase.soprafs21.constant.SetCategory;
import ch.uzh.ifi.hase.soprafs21.constant.SetStatus;
import ch.uzh.ifi.hase.soprafs21.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs21.entity.*;
import ch.uzh.ifi.hase.soprafs21.rest.dto.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * DTOMapperTest
 * Tests if the mapping between the internal and the external/API representation works.
 */

public class DTOMapperTest {
    @Test
    public void testCreateUser_fromUserPostDTO_toUser_success() {
        // create UserPostDTO
        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setName("name");
        userPostDTO.setUsername("username");
        userPostDTO.setPassword("password");
        userPostDTO.setEmail("name@email.com");
        userPostDTO.setToken("1");
        userPostDTO.setInGame(false);
        userPostDTO.setNumberOfWins(1);


        // MAP -> Create user
        User user = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        // check content
        assertEquals(userPostDTO.getName(), user.getName());
        assertEquals(userPostDTO.getUsername(), user.getUsername());
        assertEquals(userPostDTO.getPassword(), user.getPassword());
        assertEquals(userPostDTO.getEmail(), user.getEmail());
        assertEquals(userPostDTO.getToken(), user.getToken());
        assertEquals(0L, user.getUserId());
        assertEquals(userPostDTO.isInGame(), user.getInGame());
        assertEquals(userPostDTO.getNumberOfWins(), user.getNumberOfWins());

    }

    @Test
    public void testGetUser_fromUser_toUserGetDTO_success() {
        // create empty List for cards
        List<Set> emptyList = new ArrayList<>();

        // create User
        User user = new User();
        user.setName("Firstname Lastname");
        user.setUsername("firsty");
        user.setEmail("first@email.com");
        user.setPassword("password");
        user.setCreatedSets(emptyList);
        user.setStatus(UserStatus.OFFLINE);
        user.setToken("1");
        user.setInGame(false);
        user.setNumberOfWins(1);

        // MAP -> Create UserGetDTO
        UserGetDTO userGetDTO = DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);

        // check content
        assertEquals(user.getUserId(), userGetDTO.getUserId());
        assertEquals(user.getName(), userGetDTO.getName());
        assertEquals(user.getUsername(), userGetDTO.getUsername());
        assertEquals(user.getStatus(), userGetDTO.getStatus());
        assertEquals(user.getPassword(), userGetDTO.getPassword());
        assertEquals(user.getToken(), userGetDTO.getToken());
        assertEquals(user.getCreatedSets(), userGetDTO.getCreatedSets());
        assertEquals(user.getEmail(), userGetDTO.getEmail());
        assertEquals(user.getInGame(), userGetDTO.isInGame());
        assertEquals(user.getNumberOfWins(), userGetDTO.getNumberOfWins());
    }

    @Test
    public void testCreateSet_fromSetPostDTO_toSet_success() {
        // create list of cards
        List<Card> emptyList = new ArrayList<>();

        // create User
        User user = new User();
        user.setName("Firstname Lastname");
        user.setUsername("firsty");
        user.setEmail("first@email.com");
        user.setPassword("password");
        user.setStatus(UserStatus.OFFLINE);
        user.setToken("1");

        // create setPostDTO
        SetPostDTO setPostDTO = new SetPostDTO();
        setPostDTO.setTitle("title");
        setPostDTO.setUser(user);
        setPostDTO.setCards(emptyList);
        setPostDTO.setSetCategory(SetCategory.BIOLOGY);
        setPostDTO.setSetStatus(SetStatus.PUBLIC);
        setPostDTO.setExplain("explain");
        setPostDTO.setPhoto("photo");

        // Map -> create Set
        Set set = DTOMapper.INSTANCE.convertSetPostDTOtoEntity(setPostDTO);

        //check Content
        assertEquals(setPostDTO.getTitle(),set.getTitle());
        assertEquals(setPostDTO.getUser().getUserId(), set.getUser());
        assertEquals(setPostDTO.getCards(), set.getCards());
        assertEquals(setPostDTO.getSetCategory(), set.getSetCategory());
        assertEquals(setPostDTO.getSetStatus(), set.getSetStatus());
        assertEquals(setPostDTO.getExplain(), set.getExplain());
        assertEquals(setPostDTO.getPhoto(), set.getPhoto());
    }

    @Test
    public void testGetSet_fromSet_toSetGetDTO_success() {
        // create list of cards
        List<Card> emptyList = new ArrayList<>();

        // create User
        User user = new User();
        user.setName("Firstname Lastname");
        user.setUsername("firsty");
        user.setEmail("first@email.com");
        user.setPassword("password");
        user.setStatus(UserStatus.OFFLINE);
        user.setToken("1");
        user.setLearnSets(new ArrayList<>());

        // create setPostDTO
        Set set = new Set();
        set.setTitle("title");
        set.setUser(user);
        set.setCards(emptyList);
        set.setSetCategory(SetCategory.BIOLOGY);
        set.setSetStatus(SetStatus.PUBLIC);
        set.setExplain("explain");
        set.setPhoto("photo");
        set.setMembers(new ArrayList<>());

        // Map -> create Set
        SetGetDTO setGetDTO = DTOMapper.INSTANCE.convertEntityToSetGetDTO(set);

        //check Content
        assertEquals(setGetDTO.getSetId(), set.getSetId());
        assertEquals(setGetDTO.getTitle(),set.getTitle());
        assertEquals(setGetDTO.getUserId(), set.getUser());
        assertEquals(setGetDTO.getCards(), set.getCards());
        assertEquals(setGetDTO.getSetCategory(), set.getSetCategory());
        assertEquals(setGetDTO.getSetStatus(), set.getSetStatus());
        assertEquals(setGetDTO.getExplain(), set.getExplain());
        assertEquals(setGetDTO.getPhoto(), set.getPhoto());
        assertEquals(setGetDTO.getMemberIds(), set.getMembers());
    }

    @Test
    public void testCreateSettings_fromSettingsPostDTO_toSettings_success() {
        //create cardOrderList
        ArrayList<Long> cardOrder = new ArrayList<>();
        cardOrder.add(0L);
        cardOrder.add(1L);

        // create Settings
        SettingsPostDTO settingsPostDTO = new SettingsPostDTO();
        settingsPostDTO.setSetID(0L);
        settingsPostDTO.setUserID(0l);
        settingsPostDTO.setSetID(0L);
        settingsPostDTO.setCardsShuffled(false);
        settingsPostDTO.setStudyStarred(false);
        settingsPostDTO.setLastCard(0L);
        settingsPostDTO.setSavedOrder(cardOrder);
        settingsPostDTO.setMarkedCards(cardOrder);

        // Map -> get Settings
        Settings settings = DTOMapper.INSTANCE.convertSettingsPostDTOtoEntity(settingsPostDTO);

        // assert correctness
        assertEquals(settingsPostDTO.getSettingsId(), settings.getSettingsId() );
        assertEquals(settingsPostDTO.getUserID(), settings.getUserID());
        assertEquals(settingsPostDTO.getSetID(), settings.getSetID());
        assertEquals(settingsPostDTO.getCardsShuffled(), settings.getCardsShuffled());
        assertEquals(settingsPostDTO.getStudyStarred(), settings.getStudyStarred());
        assertEquals(settingsPostDTO.getLastCard(), settings.getLastCard());
        assertEquals(settingsPostDTO.getSavedOrder(), settings.getSavedOrder());
        assertEquals(settingsPostDTO.getMarkedCards(), settings.getMarkedCards());

    }

    @Test
    public void testGetSettings_fromSettings_toSettingsGetDTO_success(){
        // create cardOrderList
        ArrayList<Long> cardOrder = new ArrayList<>();
        cardOrder.add(0L);
        cardOrder.add(1L);

        // create settings
        Settings settings = new Settings();
        settings.setSetID(0L);
        settings.setUserID(0l);
        settings.setSetID(0L);
        settings.setCardsShuffled(false);
        settings.setStudyStarred(false);
        settings.setLastCard(0L);
        settings.setSavedOrder(cardOrder);
        settings.setMarkedCards(cardOrder);

        // Map -> get SettingsGetDTO
        SettingsGetDTO settingsGetDTO = DTOMapper.INSTANCE.convertEntityToSettingsGetDTO(settings);

        // assert Correctness
        assertEquals(settingsGetDTO.getSettingsId(), settings.getSettingsId() );
        assertEquals(settingsGetDTO.getUserID(), settings.getUserID());
        assertEquals(settingsGetDTO.getSetID(), settings.getSetID());
        assertEquals(settingsGetDTO.getCardsShuffled(), settings.getCardsShuffled());
        assertEquals(settingsGetDTO.getStudyStarred(), settings.getStudyStarred());
        assertEquals(settingsGetDTO.getLastCard(), settings.getLastCard());
        assertEquals(settingsGetDTO.getSavedOrder(), settings.getSavedOrder());
        assertEquals(settingsGetDTO.getMarkedCards(), settings.getMarkedCards());

    }

    @Test
    public void testGetGame_fromGame_toGameGetDTO_success(){

        // given
        GameSetting gameSetting = new GameSetting();
        gameSetting.setGameSettingId(1L);
        gameSetting.setTime(100L);
        gameSetting.setNumberOfCards(10L);
        gameSetting.setNumberOfPlayers(2L);

        // create Game
        Game game = new Game();
        game.setGameId(1L);
        game.setStatus(GameStatus.OPEN);
        game.setGameSettings(gameSetting);
        game.setInviter(new User());
        game.setPlaySetId(1L);
        game.setPlayers(Collections.singletonList(new User()));
        game.setCountDown(false);
        game.setTimer(10L);
        game.setHistory(Collections.singletonList(new Message()));

        // Map -> get SettingsGetDTO
        GameGetDTO gameGetDTO = DTOMapper.INSTANCE.convertEntityToGameGetDTO(game);

        // assert Correctness
        assertEquals(gameGetDTO.getGameId(), game.getGameId() );
        assertEquals(gameGetDTO.getStatus(), game.getStatus());
        assertEquals(gameGetDTO.getGameSettings(), game.getGameSettings());
        assertEquals(gameGetDTO.getInviter(), game.getInviter());
        assertEquals(gameGetDTO.getPlaySetId(), game.getPlaySetId());
        assertEquals(gameGetDTO.getPlayers(), game.getPlayers());
        assertEquals(gameGetDTO.getTimer(), game.getTimer());
        assertEquals(gameGetDTO.getHistory(), game.getHistory());

    }

    @Test
    public void testGetGame_fromGamePostDTO_toGame_success(){

        // given
        GameSetting gameSetting = new GameSetting();
        gameSetting.setGameSettingId(1L);
        gameSetting.setTime(100L);
        gameSetting.setNumberOfCards(10L);
        gameSetting.setNumberOfPlayers(2L);

        // create Game
        GamePostDTO gamePostDTO = new GamePostDTO();
        gamePostDTO.setGameId(1L);
        gamePostDTO.setStatus(GameStatus.OPEN);
        gamePostDTO.setGameSettings(gameSetting);
        gamePostDTO.setInviter(new User());
        gamePostDTO.setPlaySetId(1L);
        gamePostDTO.setCountDown(false);
        gamePostDTO.setTimer(10L);
        gamePostDTO.setHistory(Collections.singletonList(new Message()));

        // Map -> get SettingsGetDTO
        Game game = DTOMapper.INSTANCE.convertGamePostDTOtoEntity(gamePostDTO);

        // assert Correctness
        assertEquals(gamePostDTO.getGameId(), game.getGameId() );
        assertEquals(gamePostDTO.getStatus(), game.getStatus());
        assertEquals(gamePostDTO.getGameSettings(), game.getGameSettings());
        assertEquals(gamePostDTO.getInviter(), game.getInviter());
        assertEquals(gamePostDTO.getPlaySetId(), game.getPlaySetId());
        assertEquals(gamePostDTO.getTimer(), game.getTimer());
        assertEquals(gamePostDTO.getHistory(), game.getHistory());

    }

    @Test
    public void testGetInvitation_fromInvitation_toInvitationGetDTO_success(){

        // given
        GameSetting gameSetting = new GameSetting();
        gameSetting.setGameSettingId(1L);
        gameSetting.setTime(100L);
        gameSetting.setNumberOfCards(10L);
        gameSetting.setNumberOfPlayers(2L);

        // create Invitation
        Invitation invitation = new Invitation();
        invitation.setInvitationId(1L);
        invitation.setGameId(1L);
        invitation.setSentFromId(1L);
        invitation.setSentFromUserName("user");
        invitation.setReceivers(Collections.singletonList(new User()));
        invitation.setSetTitle("title");
        invitation.setGameSetting(gameSetting);

        // Map -> get SettingsGetDTO
        InvitationGetDTO invitationGetDTO = DTOMapper.INSTANCE.convertEntityToInvitationGetDTO(invitation);

        // assert Correctness
        assertEquals(invitationGetDTO.getGameId(), invitation.getGameId() );
        assertEquals(invitationGetDTO.getInvitationId(), invitation.getInvitationId());
        assertEquals(invitationGetDTO.getSentFromUserName(), invitation.getSentFromUserName());
        assertEquals(invitationGetDTO.getSentFromId(), invitation.getSentFromId());
        assertEquals(invitationGetDTO.getReceivers(), invitation.getReceivers());
        assertEquals(invitationGetDTO.getSetTitle(), invitation.getSetTitle());
        assertEquals(invitationGetDTO.getGameSetting(), invitation.getGameSetting());

    }

    @Test
    public void testGetInvitation_fromInvitationPostDTO_toInvitation_success(){

        // given
        GameSetting gameSetting = new GameSetting();
        gameSetting.setGameSettingId(1L);
        gameSetting.setTime(100L);
        gameSetting.setNumberOfCards(10L);
        gameSetting.setNumberOfPlayers(2L);

        // create Invitation
        InvitationPostDTO invitationPostDTO = new InvitationPostDTO();
        invitationPostDTO.setInvitationId(1L);
        invitationPostDTO.setGameId(1L);
        invitationPostDTO.setSentFromId(1L);
        invitationPostDTO.setSentFromUserName("user");
        invitationPostDTO.setSetTitle("title");
        invitationPostDTO.setGameSetting(gameSetting);

        // Map -> get SettingsGetDTO
        Invitation invitation = DTOMapper.INSTANCE.convertInvitationPostDTOToEntity(invitationPostDTO);

        // assert Correctness
        assertEquals(invitationPostDTO.getGameId(), invitation.getGameId() );
        assertEquals(invitationPostDTO.getInvitationId(), invitation.getInvitationId());
        assertEquals(invitationPostDTO.getSentFromUserName(), invitation.getSentFromUserName());
        assertEquals(invitationPostDTO.getSentFromId(), invitation.getSentFromId());
        assertEquals(invitationPostDTO.getSetTitle(), invitation.getSetTitle());
        assertEquals(invitationPostDTO.getGameSetting(), invitation.getGameSetting());

    }

    @Test
    public void testPostMessage_fromMessagePostDTO_toMessage_success(){

        // create Message
        MessagePostDTO messagePostDTO = new MessagePostDTO();
        messagePostDTO.setTimeStamp(LocalDateTime.now());
        messagePostDTO.setSenderId(1L);
        messagePostDTO.setMessage("message");
        messagePostDTO.setCardId(1L);
        messagePostDTO.setScore(1L);

        // Map -> get SettingsGetDTO
        Message message = DTOMapper.INSTANCE.convertMessagePostDTOtoEntity(messagePostDTO);

        // assert Correctness
        assertEquals(messagePostDTO.getTimeStamp(), message.getTimeStamp() );
        assertEquals(messagePostDTO.getSenderId(), message.getSenderId());
        assertEquals(messagePostDTO.getMessage(), message.getMessage());
        assertEquals(messagePostDTO.getCardId(), message.getCardId());
        assertEquals(messagePostDTO.getScore(), message.getScore());

    }

}
