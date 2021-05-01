package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Card;
import ch.uzh.ifi.hase.soprafs21.entity.Set;
import ch.uzh.ifi.hase.soprafs21.entity.Settings;
import ch.uzh.ifi.hase.soprafs21.repository.SetRepository;
import ch.uzh.ifi.hase.soprafs21.repository.SettingsRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

public class SettingsServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SetRepository setRepository;

    @Mock
    private SettingsRepository settingsRepository;

    @InjectMocks
    private SettingsService settingsService;

    private Settings testSettings;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // given
        testSettings = new Settings();
        testSettings.setUserID(1L);
        testSettings.setSetID(2L);
        testSettings.setCardsShuffled(true);
        testSettings.setStudyStarred(true);
        testSettings.setLastCard(2L);
        testSettings.setMarkedCards(Arrays.asList(1L,2L,3L));
        testSettings.setSavedOrder(Arrays.asList(4L,5L,6L));

        // when -> any object is being save in the setRepository -> return the dummy testUser
        Mockito.when(settingsRepository.save(Mockito.any())).thenReturn(testSettings);

    }

    @Test
    public void getSettings_validInputs_success() {

        Mockito.when(settingsRepository.existsByUserIDAndSetID(Mockito.any(), Mockito.any())).thenReturn(true);

        settingsService.getSettings(testSettings.getUserID(), testSettings.getSetID());

        verify(settingsRepository).findByUserIDAndSetID(Mockito.any(), Mockito.any());
    }

    @Test
    public void getSettings_validInputs_UnSuccess() {

        Mockito.when(settingsRepository.existsByUserIDAndSetID(Mockito.any(), Mockito.any())).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> settingsService.getSettings(testSettings.getUserID(), testSettings.getSetID()));

        Exception e = assertThrows(ResponseStatusException.class, () -> settingsService.getSettings(testSettings.getUserID(), testSettings.getSetID()));
        assertEquals("400 BAD_REQUEST \"No settings file found with this userId or setId\"", e.getMessage());

    }

    @Test
    public void getAllSettings_validInputs() {

        settingsService.getAllSettings();

        verify(settingsRepository).findAll();

    }

    @Test
    public void updateSettings_validInputs_settingsDoesExist_success() {

        Mockito.when(userRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.when(settingsRepository.existsByUserIDAndSetID(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.when(settingsRepository.findByUserIDAndSetID(Mockito.any(), Mockito.any())).thenReturn(testSettings);

        Settings createdSettings = settingsService.updateSettings(testSettings);

        assertEquals(testSettings.getUserID(), createdSettings.getUserID());
        assertEquals(testSettings.getSetID(), createdSettings.getSetID());
        assertEquals(testSettings.getCardsShuffled(), createdSettings.getCardsShuffled());
        assertEquals(testSettings.getStudyStarred(), createdSettings.getStudyStarred());
        assertEquals(testSettings.getLastCard(), createdSettings.getLastCard());
        assertEquals(testSettings.getMarkedCards(), createdSettings.getMarkedCards());
        assertEquals(testSettings.getSavedOrder(), createdSettings.getSavedOrder());

    }

    @Test
    public void updateSettings_UserDoesntExist_UnSuccess() {

        Mockito.when(userRepository.existsById(Mockito.any())).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> settingsService.updateSettings(testSettings));

        Exception e = assertThrows(ResponseStatusException.class, () -> settingsService.updateSettings(testSettings));
        assertEquals("400 BAD_REQUEST \"User doesn't exist\"", e.getMessage());
    }

    @Test
    public void updateCardOrder_Success() {

        Card card1 = new Card();
        card1.setCardId(3L);
        Card card2 = new Card();
        card2.setCardId(8L);
        Card card3 = new Card();
        card3.setCardId(9L);
        List<Card> cardList = new ArrayList<>();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);

        Set createdSet = new Set();
        createdSet.setCards(cardList);

        List<Settings> settingsFiles = new ArrayList<Settings>();
        settingsFiles.add(testSettings);

        Mockito.when(setRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.when(settingsRepository.findAllBySetID(Mockito.any())).thenReturn(settingsFiles);

        settingsService.updateCardOrder(createdSet);

        for (Settings settings: settingsFiles){
            assertEquals(settings.getCardsShuffled(), false);
            assertEquals(settings.getStudyStarred(), false);
            assertEquals(settings.getLastCard(), 3L);
            assertEquals(settings.getMarkedCards(), Collections.singletonList(3L));
            assertEquals(settings.getSavedOrder(), Arrays.asList(3L,8L,9L));
        }

    }


    @Test
    public void updateCardOrder_UnSuccess_SetDoesntExist() {

        Card card1 = new Card();
        card1.setCardId(3L);
        Card card2 = new Card();
        card2.setCardId(8L);
        Card card3 = new Card();
        card3.setCardId(9L);
        List<Card> cardList = new ArrayList<>();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);

        Set createdSet = new Set();
        createdSet.setCards(cardList);

        Mockito.when(setRepository.existsById(Mockito.any())).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> settingsService.updateCardOrder(createdSet));

        Exception e = assertThrows(ResponseStatusException.class, () -> settingsService.updateCardOrder(createdSet));
        assertEquals("400 BAD_REQUEST \"Set doesn't exist\"", e.getMessage());
    }

    @Test
    public void updateCardOrder_UnSuccess_SettingsDoesntExist() {

        Card card1 = new Card();
        card1.setCardId(3L);
        Card card2 = new Card();
        card2.setCardId(8L);
        Card card3 = new Card();
        card3.setCardId(9L);
        List<Card> cardList = new ArrayList<>();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);

        Set createdSet = new Set();
        createdSet.setCards(cardList);

        List<Settings> settingsFiles = new ArrayList<Settings>();

        Mockito.when(setRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.when(settingsRepository.findAllBySetID(Mockito.any())).thenReturn(settingsFiles);

        assertThrows(ResponseStatusException.class, () -> settingsService.updateCardOrder(createdSet));

        Exception e = assertThrows(ResponseStatusException.class, () -> settingsService.updateCardOrder(createdSet));
        assertEquals("400 BAD_REQUEST \"No Settingfiles were found when updating set\"", e.getMessage());
    }

    @Test
    public void updateStarredCards_Success() {

        List<Long> newCardIds = Arrays.asList(1L,3L,23L);


        List<Settings> settingsFiles = new ArrayList<Settings>();
        settingsFiles.add(testSettings);

        settingsService.updateStarredCards(newCardIds, settingsFiles);

        for (Settings settings: settingsFiles){
            assertEquals(settings.getMarkedCards(), Arrays.asList(1L,3L));
        }
    }

    @Test
    public void createSettings_Success() {

        Card card1 = new Card();
        card1.setCardId(13L);
        Card card2 = new Card();
        card2.setCardId(14L);
        Card card3 = new Card();
        card3.setCardId(15L);
        List<Card> cardList = new ArrayList<>();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);

        Set createdSet = new Set();
        createdSet.setCards(cardList);

        Mockito.when(userRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.when(setRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.when(setRepository.findById(Mockito.any())).thenReturn(Optional.of(createdSet));

        settingsService.createSettings(10L,20L);

        verify(settingsRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void createSettings_UnSuccess_UserDoesntExist() {

        Card card1 = new Card();
        card1.setCardId(13L);
        Card card2 = new Card();
        card2.setCardId(14L);
        Card card3 = new Card();
        card3.setCardId(15L);
        List<Card> cardList = new ArrayList<>();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);

        Set createdSet = new Set();
        createdSet.setCards(cardList);

        Mockito.when(userRepository.existsById(Mockito.any())).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> settingsService.createSettings(10L,20L));

        Exception e = assertThrows(ResponseStatusException.class, () -> settingsService.createSettings(10L,20L));
        assertEquals("400 BAD_REQUEST \"User not found with userId while creating settings file\"", e.getMessage());
    }

    @Test
    public void createSettings_UnSuccess_SetDoesntExist() {

        Card card1 = new Card();
        card1.setCardId(13L);
        Card card2 = new Card();
        card2.setCardId(14L);
        Card card3 = new Card();
        card3.setCardId(15L);
        List<Card> cardList = new ArrayList<>();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);

        Set createdSet = new Set();
        createdSet.setCards(cardList);

        Mockito.when(userRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.when(setRepository.existsById(Mockito.any())).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> settingsService.createSettings(10L,20L));

        Exception e = assertThrows(ResponseStatusException.class, () -> settingsService.createSettings(10L,20L));
        assertEquals("400 BAD_REQUEST \"Set not found with setId while creating settings file\"", e.getMessage());
    }
}
