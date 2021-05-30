package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.constant.SetCategory;
import ch.uzh.ifi.hase.soprafs21.constant.SetStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Set;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.SetPostDTO;
import ch.uzh.ifi.hase.soprafs21.service.SetService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SetController.class)
public class SetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SetService setService;

    @MockBean
    private SettingsService settingsService;


    @Test
    public void givenPublicSet_whenGetSets_thenReturnJsonArray() throws Exception {

        // given
        Set set = new Set();
        set.setSetId(1L);
        set.setTitle("Set example");
        set.setExplain("explain text");
        set.setUser(new User());
        set.setMembers(Collections.singletonList(new User()));
        set.setCards(new ArrayList<>());
        set.setSetCategory(SetCategory.GERMAN);
        set.setSetStatus(SetStatus.PUBLIC);
        set.setPhoto("Photo#1");
        set.setLiked(7L);


        List<Set> allSets = Collections.singletonList(set);

        // this mocks the UserService -> we define above what the userService should return when getUsers() is called
        given(setService.getPublicSets()).willReturn(allSets);


        // when
        MockHttpServletRequestBuilder getRequest = get("/sets").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].setId", is(1)))
                .andExpect(jsonPath("$[0].title", is(set.getTitle())))
                .andExpect(jsonPath("$[0].explain", is(set.getExplain())))
                .andExpect(jsonPath("$[0].userId", is(set.getUser())))
                .andExpect(jsonPath("$[0].memberIds", is(set.getMembers())))
                .andExpect(jsonPath("$[0].cards", is(set.getCards())))
                .andExpect(jsonPath("$[0].setCategory", is(set.getSetCategory().toString())))
                .andExpect(jsonPath("$[0].setStatus", is(set.getSetStatus().toString())))
                .andExpect(jsonPath("$[0].photo", is(set.getPhoto())))
                .andExpect(jsonPath("$[0].liked", is(set.getLiked().intValue())));

    }

    @Test
    public void givenSet_whenGetSetsById_thenReturnJsonArray() throws Exception {

        // given
        Set set = new Set();
        set.setSetId(1L);
        set.setTitle("Set example");
        set.setExplain("explain text");
        set.setUser(new User());
        set.setMembers(Collections.singletonList(new User()));
        set.setCards(new ArrayList<>());
        set.setSetCategory(SetCategory.GERMAN);
        set.setSetStatus(SetStatus.PUBLIC);
        set.setPhoto("Photo#1");
        set.setLiked(7L);

        // this mocks the UserService -> we define above what the userService should return when getUsers() is called
        given(setService.getSetBySetId(Mockito.eq(1L))).willReturn(set);


        // when
        MockHttpServletRequestBuilder getRequest = get("/sets/1").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isAccepted())
                .andExpect(jsonPath("$.*", hasSize(10)))
                .andExpect(jsonPath("$.setId", is(1)))
                .andExpect(jsonPath("$.title", is(set.getTitle())))
                .andExpect(jsonPath("$.explain", is(set.getExplain())))
                .andExpect(jsonPath("$.userId", is(set.getUser())))
                .andExpect(jsonPath("$.memberIds", is(set.getMembers())))
                .andExpect(jsonPath("$.cards", is(set.getCards())))
                .andExpect(jsonPath("$.setCategory", is(set.getSetCategory().toString())))
                .andExpect(jsonPath("$.setStatus", is(set.getSetStatus().toString())))
                .andExpect(jsonPath("$.photo", is(set.getPhoto())))
                .andExpect(jsonPath("$.liked", is(set.getLiked().intValue())));

    }

    @Test
    public void givenSet_updateSet_validInput() throws Exception {
        // given
        Set newSet = new Set();
        newSet.setSetId(1L);
        newSet.setTitle("New Set example");
        newSet.setExplain("New explain text");
        newSet.setUser(new User());
        newSet.setMembers(Collections.singletonList(new User()));
        newSet.setCards(new ArrayList<>());
        newSet.setSetCategory(SetCategory.GERMAN);
        newSet.setSetStatus(SetStatus.PUBLIC);
        newSet.setPhoto("New Photo#1");
        newSet.setLiked(7L);

        SetPostDTO setPostDTO = new SetPostDTO();
        setPostDTO.setTitle("Set example");
        setPostDTO.setExplain("explain text");
        setPostDTO.setUser(new User());
        setPostDTO.setMembers(Collections.singletonList(new User()));
        setPostDTO.setCards(new ArrayList<>());
        setPostDTO.setSetCategory(SetCategory.ENGLISH);
        setPostDTO.setSetStatus(SetStatus.PUBLIC);
        setPostDTO.setPhoto("Photo#1");
        setPostDTO.setLiked(10L);

        given(setService.updateSet(Mockito.any())).willReturn(newSet);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder putRequest = put("/sets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(setPostDTO));

        // then
        mockMvc.perform(putRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(10)))
                .andExpect(jsonPath("$.setId", is(1)))
                .andExpect(jsonPath("$.title", is(newSet.getTitle())))
                .andExpect(jsonPath("$.explain", is(newSet.getExplain())))
                .andExpect(jsonPath("$.userId", is(newSet.getUser())))
                .andExpect(jsonPath("$.memberIds", is(newSet.getMembers())))
                .andExpect(jsonPath("$.cards", is(newSet.getCards())))
                .andExpect(jsonPath("$.setCategory", is(newSet.getSetCategory().toString())))
                .andExpect(jsonPath("$.setStatus", is(newSet.getSetStatus().toString())))
                .andExpect(jsonPath("$.photo", is(newSet.getPhoto())))
                .andExpect(jsonPath("$.liked", is(newSet.getLiked().intValue())));
    }

    @Test
    public void givenSet_updateMembersSet_validInput() throws Exception {
        // given
        User testUser = new User();
        testUser.setUserId(1L);
        testUser.setUsername("testUser");
        testUser.setPassword("password");

        User testUser2 = new User();
        testUser.setUserId(2L);
        testUser.setUsername("testUser2");
        testUser.setPassword("password2");

        ArrayList<User> members = new ArrayList<>();
        members.add(testUser);
        members.add(testUser2);

        Set newSet = new Set();
        newSet.setSetId(1L);
        newSet.setTitle("New Set example");
        newSet.setExplain("New explain text");
        newSet.setUser(new User());
        newSet.setMembers(members);
        newSet.setCards(new ArrayList<>());
        newSet.setSetCategory(SetCategory.GERMAN);
        newSet.setSetStatus(SetStatus.PUBLIC);
        newSet.setPhoto("New Photo#1");
        newSet.setLiked(7L);



        given(setService.addMember(Mockito.eq(2L), Mockito.eq(1L))).willReturn(newSet);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder putRequest = put("/sets/2/1")
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(putRequest).andExpect(status().isAccepted())
                .andExpect(jsonPath("$.*", hasSize(10)))
                .andExpect(jsonPath("$.setId", is(1)))
                .andExpect(jsonPath("$.title", is(newSet.getTitle())))
                .andExpect(jsonPath("$.explain", is(newSet.getExplain())))
                .andExpect(jsonPath("$.userId", is(newSet.getUser())))
                .andExpect(jsonPath("$.memberIds[0]", is(newSet.getMembers().get(0).intValue())))
                .andExpect(jsonPath("$.cards", is(newSet.getCards())))
                .andExpect(jsonPath("$.setCategory", is(newSet.getSetCategory().toString())))
                .andExpect(jsonPath("$.setStatus", is(newSet.getSetStatus().toString())))
                .andExpect(jsonPath("$.photo", is(newSet.getPhoto())))
                .andExpect(jsonPath("$.liked", is(newSet.getLiked().intValue())));
    }

    @Test
    public void createSet_validInput_setCreated() throws Exception {
        // given
        Set newSet = new Set();
        newSet.setSetId(1L);
        newSet.setTitle("Set example");
        newSet.setExplain("explain text");
        newSet.setUser(new User());
        newSet.setMembers(Collections.singletonList(new User()));
        newSet.setCards(new ArrayList<>());
        newSet.setSetCategory(SetCategory.GERMAN);
        newSet.setSetStatus(SetStatus.PUBLIC);
        newSet.setPhoto("Photo#1");
        newSet.setLiked(7L);

        SetPostDTO setPostDTO = new SetPostDTO();
        setPostDTO.setTitle("Set example");
        setPostDTO.setExplain("explain text");
        setPostDTO.setUser(new User());
        setPostDTO.setMembers(Collections.singletonList(new User()));
        setPostDTO.setCards(new ArrayList<>());
        setPostDTO.setSetCategory(SetCategory.ENGLISH);
        setPostDTO.setSetStatus(SetStatus.PUBLIC);
        newSet.setPhoto("Photo#1");
        newSet.setLiked(10L);

        given(setService.createSet(Mockito.any())).willReturn(newSet);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/sets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(setPostDTO));

        // then
        mockMvc.perform(postRequest).andExpect(status().isCreated())
                .andExpect(jsonPath("$.*", hasSize(10)))
                .andExpect(jsonPath("$.setId", is(1)))
                .andExpect(jsonPath("$.title", is(newSet.getTitle())))
                .andExpect(jsonPath("$.explain", is(newSet.getExplain())))
                .andExpect(jsonPath("$.userId", is(newSet.getUser())))
                .andExpect(jsonPath("$.memberIds", is(newSet.getMembers())))
                .andExpect(jsonPath("$.cards", is(newSet.getCards())))
                .andExpect(jsonPath("$.setCategory", is(newSet.getSetCategory().toString())))
                .andExpect(jsonPath("$.setStatus", is(newSet.getSetStatus().toString())))
                .andExpect(jsonPath("$.photo", is(newSet.getPhoto())))
                .andExpect(jsonPath("$.liked", is(newSet.getLiked().intValue())));
    }

    @Test
    public void givenSet_deleteSets_validInput() throws Exception {

        // given
        Set set = new Set();
        set.setSetId(1L);
        set.setTitle("Set example");
        set.setExplain("explain text");
        set.setUser(new User());
        set.setMembers(Collections.singletonList(new User()));
        set.setCards(new ArrayList<>());
        set.setSetCategory(SetCategory.GERMAN);
        set.setSetStatus(SetStatus.PUBLIC);
        set.setPhoto("Photo#1");
        set.setLiked(7L);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder deleteRequest = delete("/sets/1").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(deleteRequest).
                andExpect(status().isOk());

    }

    @Test
    public void givenSet_removeMember_validInput() throws Exception {

        // given
        User testUser = new User();
        testUser.setUserId(1L);
        testUser.setUsername("testUser");
        testUser.setPassword("password");

        User testUser2 = new User();
        testUser.setUserId(2L);
        testUser.setUsername("testUser2");
        testUser.setPassword("password2");

        ArrayList<User> members = new ArrayList<>();
        members.add(testUser);
        members.add(testUser2);

        Set set = new Set();
        set.setSetId(1L);
        set.setTitle("Set example");
        set.setExplain("explain text");
        set.setUser(new User());
        set.setMembers(members);
        set.setCards(new ArrayList<>());
        set.setSetCategory(SetCategory.GERMAN);
        set.setSetStatus(SetStatus.PUBLIC);
        set.setPhoto("Photo#1");
        set.setLiked(7L);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder deleteRequest = delete("/sets/1/2").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(deleteRequest).
                andExpect(status().isAccepted());

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
