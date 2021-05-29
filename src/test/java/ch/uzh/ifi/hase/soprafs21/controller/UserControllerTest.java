package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPostDTO;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserControllerTest
 * This is a WebMvcTest which allows to test the UserController i.e. GET/POST request without actually sending them over the network.
 * This tests if the UserController works.
 */
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {

        // given
        User user = new User();
        user.setUserId(1L);
        user.setName("Firstname Lastname");
        user.setUsername("firstname@lastname");
        user.setStatus(UserStatus.OFFLINE);
        user.setPassword("password");
        user.setCreatedSets(new ArrayList<>());
        user.setLearnSets(new ArrayList<>());

        List<User> allUsers = Collections.singletonList(user);

        // this mocks the UserService -> we define above what the userService should return when getUsers() is called
        given(userService.getUsers()).willReturn(allUsers);


        // when
        MockHttpServletRequestBuilder getRequest = get("/users").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userId", is(1)))
                .andExpect(jsonPath("$[0].name", is(user.getName())))
                .andExpect(jsonPath("$[0].username", is(user.getUsername())))
                .andExpect(jsonPath("$[0].status", is(user.getStatus().toString())))
                .andExpect(jsonPath("$[0].password", is(user.getPassword())))
                .andExpect(jsonPath("$[0].createdSets", is(user.getCreatedSets())))
                .andExpect(jsonPath("$[0].learnSets", is(user.getLearnSets())));
    }

    @Test
    public void givenUsers_whenOnlineUsers_thenReturnJsonArray() throws Exception {

        // given
        User user = new User();
        user.setUserId(1L);
        user.setName("Firstname Lastname");
        user.setUsername("firstname@lastname");
        user.setStatus(UserStatus.ONLINE);
        user.setPassword("password");
        user.setCreatedSets(new ArrayList<>());
        user.setLearnSets(new ArrayList<>());

        List<User> allUsers = Collections.singletonList(user);

        // this mocks the UserService -> we define above what the userService should return when getUsers() is called
        given(userService.getOnlineUsers()).willReturn(allUsers);


        // when
        MockHttpServletRequestBuilder getRequest = get("/users/online").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userId", is(1)))
                .andExpect(jsonPath("$[0].name", is(user.getName())))
                .andExpect(jsonPath("$[0].username", is(user.getUsername())))
                .andExpect(jsonPath("$[0].status", is(user.getStatus().toString())))
                .andExpect(jsonPath("$[0].password", is(user.getPassword())))
                .andExpect(jsonPath("$[0].createdSets", is(user.getCreatedSets())))
                .andExpect(jsonPath("$[0].learnSets", is(user.getLearnSets())));
    }

    @Test
    public void createUser_validInput_userCreated() throws Exception {
        // given
        User user = new User();
        user.setUserId(1L);
        user.setName("Test User");
        user.setUsername("testUsername");
        user.setToken("1");
        user.setStatus(UserStatus.ONLINE);
        user.setPassword("password");
        user.setInGame(true);
        user.setNumberOfWins(7);
        user.setEmail("xyz@gmail.com");
        user.setCreatedSets(new ArrayList<>());
        user.setLearnSets(new ArrayList<>());

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setName("Test User");
        userPostDTO.setUsername("testUsername");
        userPostDTO.setStatus(UserStatus.ONLINE);
        userPostDTO.setPassword("password");
        userPostDTO.setInGame(true);
        userPostDTO.setNumberOfWins(7);
        userPostDTO.setEmail("xyz@gmail.com");
        userPostDTO.setCreatedSets(new ArrayList<>());

        given(userService.createUser(Mockito.any())).willReturn(user);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId", is(user.getUserId().intValue())))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.status", is(user.getStatus().toString())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.inGame", is(user.getInGame())))
                .andExpect(jsonPath("$.numberOfWins", is(user.getNumberOfWins())))
                .andExpect(jsonPath("$.createdSets", is(user.getCreatedSets())))
                .andExpect(jsonPath("$.learnSets", is(user.getLearnSets())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test
    public void givenUsers_whenLoginUsers_validInput() throws Exception {
        // given
        User user = new User();
        user.setUserId(1L);
        user.setName("Test User");
        user.setUsername("testUsername");
        user.setToken("1");
        user.setStatus(UserStatus.ONLINE);
        user.setPassword("password");
        user.setInGame(true);
        user.setNumberOfWins(7);
        user.setEmail("xyz@gmail.com");
        user.setCreatedSets(new ArrayList<>());
        user.setLearnSets(new ArrayList<>());

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setName("Test User");
        userPostDTO.setUsername("testUsername");
        userPostDTO.setStatus(UserStatus.ONLINE);
        userPostDTO.setPassword("password");
        userPostDTO.setInGame(true);
        userPostDTO.setNumberOfWins(7);
        userPostDTO.setEmail("xyz@gmail.com");
        userPostDTO.setCreatedSets(new ArrayList<>());

        given(userService.checkForLogin(Mockito.any())).willReturn(user);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(user.getUserId().intValue())))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.status", is(user.getStatus().toString())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.inGame", is(user.getInGame())))
                .andExpect(jsonPath("$.numberOfWins", is(user.getNumberOfWins())))
                .andExpect(jsonPath("$.createdSets", is(user.getCreatedSets())))
                .andExpect(jsonPath("$.learnSets", is(user.getLearnSets())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test
    public void givenUsers_whenSocialLoginUsers_validInput() throws Exception {
        // given
        User user = new User();
        user.setUserId(1L);
        user.setName("Test User");
        user.setUsername("testUsername");
        user.setToken("1");
        user.setStatus(UserStatus.ONLINE);
        user.setPassword("password");
        user.setInGame(true);
        user.setNumberOfWins(7);
        user.setEmail("xyz@gmail.com");
        user.setCreatedSets(new ArrayList<>());
        user.setLearnSets(new ArrayList<>());

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setName("Test User");
        userPostDTO.setUsername("testUsername");
        userPostDTO.setStatus(UserStatus.ONLINE);
        userPostDTO.setPassword("password");
        userPostDTO.setInGame(true);
        userPostDTO.setNumberOfWins(7);
        userPostDTO.setEmail("xyz@gmail.com");
        userPostDTO.setCreatedSets(new ArrayList<>());

        given(userService.upserd(Mockito.any())).willReturn(user);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/users/socialLogin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(user.getUserId().intValue())))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.status", is(user.getStatus().toString())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.inGame", is(user.getInGame())))
                .andExpect(jsonPath("$.numberOfWins", is(user.getNumberOfWins())))
                .andExpect(jsonPath("$.createdSets", is(user.getCreatedSets())))
                .andExpect(jsonPath("$.learnSets", is(user.getLearnSets())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test
    public void givenUsers_whenGetUsersById_thenReturnJsonArray() throws Exception {

        // given
        User user = new User();
        user.setUserId(1L);
        user.setName("Firstname Lastname");
        user.setUsername("firstname@lastname");
        user.setStatus(UserStatus.OFFLINE);
        user.setPassword("password");
        user.setInGame(true);
        user.setNumberOfWins(7);
        user.setEmail("xyz@gmail.com");
        user.setCreatedSets(new ArrayList<>());
        user.setLearnSets(new ArrayList<>());

        // this mocks the UserService
        given(userService.getUser(Mockito.eq(1L))).willReturn(user);

        // when
        MockHttpServletRequestBuilder getRequest = get("/users/1").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(13)))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.status", is(user.getStatus().toString())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.inGame", is(user.getInGame())))
                .andExpect(jsonPath("$.numberOfWins", is(user.getNumberOfWins())))
                .andExpect(jsonPath("$.createdSets", is(user.getCreatedSets())))
                .andExpect(jsonPath("$.learnSets", is(user.getLearnSets())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test
    public void givenUsers_updateUser_validInput() throws Exception {
        // given
        User newUser = new User();
        newUser.setUserId(1L);
        newUser.setName("New Test User");
        newUser.setUsername("NewUsername");
        newUser.setToken("1");
        newUser.setStatus(UserStatus.ONLINE);
        newUser.setPassword("NewPassword");
        newUser.setInGame(false);
        newUser.setNumberOfWins(0);
        newUser.setEmail("xyz@gmail.com");
        newUser.setCreatedSets(new ArrayList<>());
        newUser.setLearnSets(new ArrayList<>());

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setName("Test User");
        userPostDTO.setUsername("testUsername");
        userPostDTO.setStatus(UserStatus.ONLINE);
        userPostDTO.setPassword("password");
        userPostDTO.setInGame(true);
        userPostDTO.setNumberOfWins(7);
        userPostDTO.setEmail("xyz@gmail.com");
        userPostDTO.setCreatedSets(new ArrayList<>());

        given(userService.updateUser(Mockito.any())).willReturn(newUser);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder putRequest = put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(putRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(newUser.getUserId().intValue())))
                .andExpect(jsonPath("$.name", is(newUser.getName())))
                .andExpect(jsonPath("$.username", is(newUser.getUsername())))
                .andExpect(jsonPath("$.status", is(newUser.getStatus().toString())))
                .andExpect(jsonPath("$.password", is(newUser.getPassword())))
                .andExpect(jsonPath("$.inGame", is(newUser.getInGame())))
                .andExpect(jsonPath("$.numberOfWins", is(newUser.getNumberOfWins())))
                .andExpect(jsonPath("$.createdSets", is(newUser.getCreatedSets())))
                .andExpect(jsonPath("$.learnSets", is(newUser.getLearnSets())))
                .andExpect(jsonPath("$.email", is(newUser.getEmail())));
    }

    @Test
    public void givenUsers_logoutUser_validInput() throws Exception {
        // given
        User user = new User();
        user.setUserId(1L);
        user.setName("Test User");
        user.setUsername("testUsername");
        user.setToken("1");
        user.setStatus(UserStatus.OFFLINE);
        user.setPassword("password");
        user.setInGame(true);
        user.setNumberOfWins(7);
        user.setEmail("xyz@gmail.com");
        user.setCreatedSets(new ArrayList<>());
        user.setLearnSets(new ArrayList<>());

        given(userService.logoutUser(Mockito.eq(1L))).willReturn(user);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder putRequest = put("/users/logout/1").contentType(MediaType.APPLICATION_JSON);


        // then
        mockMvc.perform(putRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(user.getUserId().intValue())))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.status", is(user.getStatus().toString())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.inGame", is(user.getInGame())))
                .andExpect(jsonPath("$.numberOfWins", is(user.getNumberOfWins())))
                .andExpect(jsonPath("$.createdSets", is(user.getCreatedSets())))
                .andExpect(jsonPath("$.learnSets", is(user.getLearnSets())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test
    public void givenUsers_deleteUser_validInput() throws Exception {
        // given
        User user = new User();
        user.setUserId(1L);
        user.setName("Test User");
        user.setUsername("testUsername");
        user.setToken("1");
        user.setStatus(UserStatus.OFFLINE);
        user.setPassword("password");
        user.setInGame(true);
        user.setNumberOfWins(7);
        user.setEmail("xyz@gmail.com");
        user.setCreatedSets(new ArrayList<>());
        user.setLearnSets(new ArrayList<>());


        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder deleteRequest = delete("/users/1").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(deleteRequest)
                .andExpect(status().isOk());
    }

    /**
     * Helper Method to convert userPostDTO into a JSON string such that the input can be processed
     * Input will look like this: {"name": "Test User", "username": "testUsername"}
     * @param object
     * @return string
     */

    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("The request body could not be created.%s", e.toString()));
        }
    }
}