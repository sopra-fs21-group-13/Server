package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Settings;
import ch.uzh.ifi.hase.soprafs21.rest.dto.SettingsPostDTO;
import ch.uzh.ifi.hase.soprafs21.service.SettingsService;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SettingsController.class)
public class SettingsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SettingsService settingsService;

    @Test
    public void givenSettings_whenGetSettings_thenReturnJsonArray() throws Exception {

        // given
        Settings settings = new Settings();
        settings.setSettingsId(1L);
        settings.setUserID(2L);
        settings.setSetID(3L);
        settings.setCardsShuffled(true);
        settings.setStudyStarred(true);
        settings.setLastCard(2L);
        settings.setMarkedCards(new ArrayList<>());
        settings.setSavedOrder(new ArrayList<>());


        List<Settings> allSettings = Collections.singletonList(settings);

        // this mocks the UserService -> we define above what the userService should return when getUsers() is called
        given(settingsService.getAllSettings()).willReturn(allSettings);


        // when
        MockHttpServletRequestBuilder getRequest = get("/settings").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].settingsId", is(1)))
                .andExpect(jsonPath("$[0].userID", is(settings.getUserID().intValue())))
                .andExpect(jsonPath("$[0].setID", is(settings.getSetID().intValue())))
                .andExpect(jsonPath("$[0].cardsShuffled", is(settings.getCardsShuffled())))
                .andExpect(jsonPath("$[0].studyStarred", is(settings.getStudyStarred())))
                .andExpect(jsonPath("$[0].lastCard", is(settings.getLastCard().intValue())))
                .andExpect(jsonPath("$[0].markedCards", is(settings.getMarkedCards())))
                .andExpect(jsonPath("$[0].savedOrder", is(settings.getSavedOrder())));

    }

    @Test
    public void givenSettings_whenGetSetByIdAndUserByUserId_thenReturnJsonArray() throws Exception {

        // given
        Settings settings = new Settings();
        settings.setSettingsId(1L);
        settings.setUserID(2L);
        settings.setSetID(3L);
        settings.setCardsShuffled(true);
        settings.setStudyStarred(true);
        settings.setLastCard(2L);
        settings.setMarkedCards(new ArrayList<>());
        settings.setSavedOrder(new ArrayList<>());

        // this mocks the UserService -> we define above what the userService should return when getUsers() is called
        given(settingsService.getSettings(Mockito.eq(2L),Mockito.eq(3L))).willReturn(settings);


        // when
        MockHttpServletRequestBuilder getRequest = get("/settings/2/3").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isAccepted())
                .andExpect(jsonPath("$.*", hasSize(8)))
                .andExpect(jsonPath("$.settingsId", is(1)))
                .andExpect(jsonPath("$.userID", is(settings.getUserID().intValue())))
                .andExpect(jsonPath("$.setID", is(settings.getSetID().intValue())))
                .andExpect(jsonPath("$.cardsShuffled", is(settings.getCardsShuffled())))
                .andExpect(jsonPath("$.studyStarred", is(settings.getStudyStarred())))
                .andExpect(jsonPath("$.lastCard", is(settings.getLastCard().intValue())))
                .andExpect(jsonPath("$.markedCards", is(settings.getMarkedCards())))
                .andExpect(jsonPath("$.savedOrder", is(settings.getSavedOrder())));

    }

    @Test
    public void givenSettings_CreateSettings_validInput() throws Exception {

        // given
        Settings NewSettings = new Settings();
        NewSettings.setSettingsId(1L);
        NewSettings.setUserID(2L);
        NewSettings.setSetID(3L);
        NewSettings.setCardsShuffled(true);
        NewSettings.setStudyStarred(true);
        NewSettings.setLastCard(2L);
        NewSettings.setMarkedCards(new ArrayList<>());
        NewSettings.setSavedOrder(new ArrayList<>());

        SettingsPostDTO settingsPostDTO = new SettingsPostDTO();
        settingsPostDTO .setUserID(4L);
        settingsPostDTO .setSetID(6L);
        settingsPostDTO .setCardsShuffled(false);
        settingsPostDTO .setStudyStarred(false);
        settingsPostDTO .setLastCard(5L);
        settingsPostDTO .setMarkedCards(new ArrayList<>());
        settingsPostDTO .setSavedOrder(new ArrayList<>());

        // this mocks the UserService -> we define above what the userService should return when getUsers() is called
        given(settingsService.updateSettings(Mockito.any())).willReturn(NewSettings);

        // when
        MockHttpServletRequestBuilder postRequest = post("/settings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(settingsPostDTO));

        // then
        mockMvc.perform(postRequest).andExpect(status().isCreated())
                .andExpect(jsonPath("$.*", hasSize(8)))
                .andExpect(jsonPath("$.settingsId", is(1)))
                .andExpect(jsonPath("$.userID", is(NewSettings.getUserID().intValue())))
                .andExpect(jsonPath("$.setID", is(NewSettings.getSetID().intValue())))
                .andExpect(jsonPath("$.cardsShuffled", is(NewSettings.getCardsShuffled())))
                .andExpect(jsonPath("$.studyStarred", is(NewSettings.getStudyStarred())))
                .andExpect(jsonPath("$.lastCard", is(NewSettings.getLastCard().intValue())))
                .andExpect(jsonPath("$.markedCards", is(NewSettings.getMarkedCards())))
                .andExpect(jsonPath("$.savedOrder", is(NewSettings.getSavedOrder())));

    }

    @Test
    public void givenSettings_UpdateSettings_validInput() throws Exception {

        // given
        Settings NewSettings = new Settings();
        NewSettings.setSettingsId(1L);
        NewSettings.setUserID(2L);
        NewSettings.setSetID(3L);
        NewSettings.setCardsShuffled(true);
        NewSettings.setStudyStarred(true);
        NewSettings.setLastCard(2L);
        NewSettings.setMarkedCards(new ArrayList<>());
        NewSettings.setSavedOrder(new ArrayList<>());

        SettingsPostDTO settingsPostDTO = new SettingsPostDTO();
        settingsPostDTO .setUserID(2L);
        settingsPostDTO .setSetID(3L);
        settingsPostDTO .setCardsShuffled(false);
        settingsPostDTO .setStudyStarred(false);
        settingsPostDTO .setLastCard(5L);
        settingsPostDTO .setMarkedCards(new ArrayList<>());
        settingsPostDTO .setSavedOrder(new ArrayList<>());

        // this mocks the UserService -> we define above what the userService should return when getUsers() is called
        given(settingsService.updateSettings(Mockito.any())).willReturn(NewSettings);

        // when
        MockHttpServletRequestBuilder postRequest = post("/settings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(settingsPostDTO));

        // then
        mockMvc.perform(postRequest).andExpect(status().isCreated())
                .andExpect(jsonPath("$.*", hasSize(8)))
                .andExpect(jsonPath("$.settingsId", is(1)))
                .andExpect(jsonPath("$.userID", is(NewSettings.getUserID().intValue())))
                .andExpect(jsonPath("$.setID", is(NewSettings.getSetID().intValue())))
                .andExpect(jsonPath("$.cardsShuffled", is(NewSettings.getCardsShuffled())))
                .andExpect(jsonPath("$.studyStarred", is(NewSettings.getStudyStarred())))
                .andExpect(jsonPath("$.lastCard", is(NewSettings.getLastCard().intValue())))
                .andExpect(jsonPath("$.markedCards", is(NewSettings.getMarkedCards())))
                .andExpect(jsonPath("$.savedOrder", is(NewSettings.getSavedOrder())));

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
