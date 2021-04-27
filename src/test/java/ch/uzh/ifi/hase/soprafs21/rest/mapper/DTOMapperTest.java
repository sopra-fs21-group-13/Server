package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.constant.SetCategory;
import ch.uzh.ifi.hase.soprafs21.constant.SetStatus;
import ch.uzh.ifi.hase.soprafs21.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Card;
import ch.uzh.ifi.hase.soprafs21.entity.Set;
import ch.uzh.ifi.hase.soprafs21.entity.Settings;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
        user.setLearnSets(emptyList);
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
        assertEquals(user.getLearnSets(), userGetDTO.getLearnSets());
        assertEquals(user.getEmail(), userGetDTO.getEmail());
        assertEquals(user.getInGame(), userGetDTO.isInGame());
        assertEquals(user.getNumberOfWins(), userGetDTO.getNumberOfWins());
    }

    @Test
    public void testCreateSet_fromSetPostDTO_fromSet_success() {
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

        // create setPostDTO
        Set set = new Set();
        set.setTitle("title");
        set.setUser(user);
        set.setCards(emptyList);
        set.setSetCategory(SetCategory.BIOLOGY);
        set.setSetStatus(SetStatus.PUBLIC);
        set.setExplain("explain");
        set.setPhoto("photo");

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

        Settings settings = DTOMapper.INSTANCE.convertSettingsPostDTOtoEntity(settingsPostDTO);

        assertEquals(settingsPostDTO.getSettingsId(), settings.getSettingsId() );
        assertEquals(settingsPostDTO.getUserID(), settings.getUserID());
        assertEquals(settingsPostDTO.getSetID(), settings.getSetID());
        assertEquals(settingsPostDTO.getCardsShuffled(), settings.getCardsShuffled());
        assertEquals(settingsPostDTO.getStudyStarred(), settings.getStudyStarred());
        assertEquals(settingsPostDTO.getLastCard(), settings.getLastCard());
        assertEquals(settingsPostDTO.getSavedOrder(), settings.getSavedOrder());
        assertEquals(settingsPostDTO.getMarkedCards(), settings.getMarkedCards());

    }

}
