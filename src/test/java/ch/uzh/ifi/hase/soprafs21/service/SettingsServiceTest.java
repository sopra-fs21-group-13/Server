package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Settings;
import ch.uzh.ifi.hase.soprafs21.repository.SettingsRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

public class SettingsServiceTest {

    @Mock
    private UserRepository userRepository;

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

        // when -> any object is being save in the userRepository -> return the dummy testUser
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
        assertEquals(testSettings.getStudyStarred(), createdSettings.getStudyStarred());
        assertEquals(testSettings.getLastCard(), createdSettings.getLastCard());
        assertEquals(testSettings.getMarkedCards(), createdSettings.getMarkedCards());
        assertEquals(testSettings.getSavedOrder(), createdSettings.getSavedOrder());

    }

    @Test
    public void updateSettings_validInputs_UnSuccess() {

        Mockito.when(userRepository.existsById(Mockito.any())).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> settingsService.updateSettings(testSettings));

        Exception e = assertThrows(ResponseStatusException.class, () -> settingsService.updateSettings(testSettings));
        assertEquals("400 BAD_REQUEST \"User doesn't exist\"", e.getMessage());
    }

    @Test
    public void updateCardOrder_Success() {

        //
    }

    @Test
    public void updateCardOrder_UnSuccess_SetDoesntExist() {

        //
    }

    @Test
    public void updateCardOrder_UnSuccess_SettingsDoesntExist() {

        //
    }

    @Test
    public void updateStarredCards_Success() {

        //
    }

    @Test
    public void createSettings_Success() {

        //
    }

    @Test
    public void createSettings_UnSuccess_UserDoesntExist() {

        //
    }

    @Test
    public void createSettings_UnSuccess_SetDoesntExist() {

        //
    }
}
