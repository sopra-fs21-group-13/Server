package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.SetCategory;
import ch.uzh.ifi.hase.soprafs21.constant.SetStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Set;
import ch.uzh.ifi.hase.soprafs21.entity.User;
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

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

public class SetServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SetRepository setRepository;

    @Mock
    private SettingsRepository settingsRepository;

    @InjectMocks
    private SetService setService;

    private Set testSet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // given
        testSet = new Set();
        testSet.setTitle("testTitle");
        testSet.setExplain("testExplain");
        testSet.setPhoto("testPhoto");
        testSet.setLiked(2L);
        testSet.setMembers(new ArrayList<>());

        // when -> any object is being save in the setRepository -> return the dummy testUser
        Mockito.when(setRepository.save(Mockito.any())).thenReturn(testSet);

    }

    @Test
    public void getPublicSets_validInputs() {

        setService.getPublicSets();

        verify(setRepository).findBySetStatus(Mockito.any());
    }

    @Test
    public void getSetBySetId_validInputs_success() {

        Mockito.when(setRepository.findBySetId(Mockito.any())).thenReturn(Optional.of(testSet));

        Set createdSet = setService.getSetBySetId(testSet.getSetId());

        assertEquals(testSet.getTitle(), createdSet.getTitle());
        assertEquals(testSet.getExplain(), createdSet.getExplain());
        assertEquals(testSet.getPhoto(), createdSet.getPhoto());
        assertEquals(testSet.getLiked(), createdSet.getLiked());
    }

    @Test
    public void getSetBySetId_validInputs_UnSuccess() {

        Mockito.when(setRepository.findBySetId(Mockito.any())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> setService.getSetBySetId(testSet.getSetId()));

        Exception e = assertThrows(ResponseStatusException.class, () -> setService.getSetBySetId(testSet.getSetId()));
        assertEquals("400 BAD_REQUEST \"No set exists with input set id.\"", e.getMessage());
    }

    @Test
    public void createSet_validInputs_success() {

        User user = new User();
        user.setUserId(1L);

        testSet.setUser(user);
        testSet.setSetCategory(SetCategory.GERMAN);
        testSet.setSetStatus(SetStatus.PUBLIC);

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));

        Set createdSet = setService.createSet(testSet);

        verify(setRepository, Mockito.times(1)).save(Mockito.any());

        assertEquals(testSet.getTitle(), createdSet.getTitle());
        assertEquals(testSet.getExplain(), createdSet.getExplain());
        assertEquals(testSet.getPhoto(), createdSet.getPhoto());
        assertEquals(testSet.getLiked(), createdSet.getLiked());

    }

    @Test
    public void createSet_validInputs_UnSuccess() {

        Set newSet = new Set();

        assertThrows(ResponseStatusException.class, () -> setService.createSet(newSet));

        Exception e = assertThrows(ResponseStatusException.class, () -> setService.createSet(newSet));
        assertEquals("400 BAD_REQUEST \"Set has no Title\"", e.getMessage());
    }

    @Test
    public void checkSet_success() {

        User user = new User();
        user.setUserId(1L);

        testSet.setUser(user);
        testSet.setSetCategory(SetCategory.GERMAN);
        testSet.setSetStatus(SetStatus.PUBLIC);

        boolean Boolean = setService.checkSet(testSet);

        assertFalse(Boolean);

    }

    @Test
    public void checkSet_UnSuccess() {

        Set newSet = new Set();

        assertThrows(ResponseStatusException.class, () -> setService.checkSet(newSet));

        Exception e = assertThrows(ResponseStatusException.class, () -> setService.checkSet(newSet));
        assertEquals("400 BAD_REQUEST \"Set has no Title\"", e.getMessage());

    }

    @Test
    public void updateSet_Success() {

        Set createdSet = new Set();
        createdSet.setTitle("testTitle2");
        createdSet.setExplain("testExplain2");
        createdSet.setPhoto("testPhoto2");
        createdSet.setLiked(20L);
        createdSet.setMembers(new ArrayList<>());

        Mockito.when(setRepository.findBySetId(Mockito.any())).thenReturn(Optional.of(testSet));

        createdSet = setService.updateSet(createdSet);

        assertEquals(testSet.getTitle(), createdSet.getTitle());
        assertEquals(testSet.getExplain(), createdSet.getExplain());
        assertEquals(testSet.getPhoto(), createdSet.getPhoto());
        assertEquals(testSet.getLiked(), createdSet.getLiked());
        assertEquals(testSet.getMembers(), createdSet.getMembers());

    }

    @Test
    public void updateSet_UnSuccess() {

        Mockito.when(setRepository.findBySetId(Mockito.any())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> setService.updateSet(testSet));

        Exception e = assertThrows(ResponseStatusException.class, () -> setService.updateSet(testSet));
        assertEquals("400 BAD_REQUEST \"No set exists with input set id.\"", e.getMessage());

    }

    @Test
    public void deleteSet_Success() {

        Mockito.when(setRepository.existsById(Mockito.any())).thenReturn(true);

        setService.deleteSet(testSet.getSetId());

        verify(setRepository).deleteById(Mockito.any());
        verify(settingsRepository).deleteBySetID(Mockito.any());

    }

    @Test
    public void deleteSet_UnSuccess() {

        Mockito.when(setRepository.existsById(Mockito.any())).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> setService.deleteSet(testSet.getSetId()));

        Exception e = assertThrows(ResponseStatusException.class, () -> setService.deleteSet(testSet.getSetId()));
        assertEquals("400 BAD_REQUEST \"Set with this SetId those not exist\"", e.getMessage());

    }
}
