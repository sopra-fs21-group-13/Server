package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPostDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

/**
 *  The method of only userService will be executed because we created the actual object of userService using @InjectMocks annotation.
 *  For dependent classes, we used mocks.
 */

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // given
        testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");

        // when -> any object is being save in the userRepository -> return the dummy testUser
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(testUser);

    }

    @Test
    public void createUser_validInputs_success() {
        // when -> any object is being save in the userRepository -> return the dummy testUser
        User createdUser = userService.createUser(testUser);

        // then
        //This verifies that the method save (you could also use existById etc. -> everything from UserRepository) was called 1 times on the mocked object.
        verify(userRepository, Mockito.times(1)).save(Mockito.any());

        assertEquals(testUser.getUserId(), createdUser.getUserId());
        assertEquals(testUser.getName(), createdUser.getName());
        assertEquals(testUser.getUsername(), createdUser.getUsername());
        assertEquals(testUser.getPassword(), createdUser.getPassword());
        assertNotNull(createdUser.getToken());
        assertEquals(UserStatus.ONLINE, createdUser.getStatus());
    }

    @Test
    public void createUser_duplicateName_throwsException() {
        // given -> a first user has already been created
        userService.createUser(testUser);

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findByName(Mockito.any())).thenReturn(testUser);
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(null);

        // then -> attempt to create second user with same user -> check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
    }

    @Test
    public void createUser_duplicateInputs_throwsException() {
        // given -> a first user has already been created
        userService.createUser(testUser);

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findByName(Mockito.any())).thenReturn(testUser);
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);


        // then -> attempt to create second user with same user -> check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
    }

    @Test
    public void getUsers_NoUser_checkForLogin() {

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(null);


        // then -> attempt to create second user with same user -> check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> userService.checkForLogin(testUser));
    }

    @Test
    public void getUsers_InCorrectPassword_checkForLogin() {

        // when -> any object is being save in the userRepository -> return the dummy testUser
        User createdUser = new User();
        createdUser.setPassword("password#2");

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);


        // then -> attempt to create second user with same user -> check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> userService.checkForLogin(createdUser));

        Exception e = assertThrows(ResponseStatusException.class, () -> userService.checkForLogin(createdUser));
        assertEquals("400 BAD_REQUEST \"Incorrect Password Or UserName\"", e.getMessage());
    }

    @Test
    public void getUsers_CorrectPassword_checkForLogin() {

        User createdUser = userService.createUser(testUser);

        // then
        //This verifies that the method save (you could also use existById etc. -> everything from UserRepository) was called 1 times on the mocked object.
        verify(userRepository, Mockito.times(1)).save(Mockito.any());

        // when -> any object is being save in the userRepository -> return the dummy testUser
        User newCreatedUser = new User();
        newCreatedUser.setPassword("password");

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(createdUser);

        userService.checkForLogin(newCreatedUser);

        assertEquals(createdUser.getUserId(), newCreatedUser.getUserId());
        assertEquals(createdUser.getName(), newCreatedUser.getName());
        assertEquals(createdUser.getPassword(), newCreatedUser.getPassword());
        assertEquals(createdUser.getToken(), newCreatedUser.getToken());
        assertEquals(createdUser.getStatus(), newCreatedUser.getStatus());

    }

    @Test
    public void getUsersByEmail_UserExistInRepository() {

        // when -> any object is being save in the userRepository -> return the dummy testUser
        User createdUser = new User();
        createdUser.setEmail("TestEmail");

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(createdUser);

        User newCreatedUser = userService.upserd(createdUser);

        assertEquals(createdUser, newCreatedUser);

    }

    @Test
    public void getUsersByEmail_UserDoesntExistInRepository() {

        // when -> any object is being save in the userRepository -> return the dummy testUser
        User createdUser = new User();
        createdUser.setEmail("TestEmail");

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(null);

        userService.upserd(createdUser);

        verify(userRepository, Mockito.times(1)).save(Mockito.any());

        assertEquals(createdUser.getUsername(), "NoName1");
        assertEquals(createdUser.getStatus(), UserStatus.ONLINE);

    }


    @Test
    public void updateUser_UserExistInRepository() {

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setUserId(1L);
        userPostDTO.setName("testName");
        userPostDTO.setUsername("testUsername");
        userPostDTO.setPassword("password");
        userPostDTO.setEmail("test email");
        userPostDTO.setInGame(true);
        userPostDTO.setNumberOfWins(23);

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(testUser));

        // user = testUser (user is testUser)
        User user = userService.updateUser(userPostDTO);

        verify(userRepository, Mockito.times(1)).save(Mockito.any());

        assertEquals(testUser.getName(), user.getName());
        assertEquals(testUser.getUsername(), user.getUsername());
        assertEquals(testUser.getPassword(), user.getPassword());
        assertEquals(testUser.getEmail(), user.getEmail());
        assertEquals(testUser.getInGame(), user.getInGame());
        assertEquals(testUser.getNumberOfWins(), user.getNumberOfWins());

    }

    @Test
    public void updateUser_UserDoesntExistInRepository() {

        // when -> any object is being save in the userRepository -> return the dummy testUser
        UserPostDTO userPostDTO = new UserPostDTO();

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        // then -> attempt to create second user with same user -> check that an error is thrown
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(userPostDTO));

    }

    @Test
    public void logoutUser_UserExistInRepository() {

        testUser.setUserId(1L);

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(testUser));

        // user = testUser (user is testUser)
        User user = userService.logoutUser(testUser.getUserId());

        verify(userRepository, Mockito.times(1)).save(Mockito.any());

        // then -> attempt to create second user with same user -> check that an error is thrown
        assertEquals(testUser.getName(), user.getName());
        assertEquals(testUser.getUsername(), user.getUsername());
        assertEquals(testUser.getPassword(), user.getPassword());
        assertEquals(user.getStatus(), UserStatus.OFFLINE);

    }

    @Test
    public void logoutUser_UserDoesntExistInRepository() {

        // when -> setup additional mocks for UserRepository
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        // then -> attempt to create second user with same user -> check that an error is thrown
        assertThrows(IllegalArgumentException.class, () -> userService.logoutUser(testUser.getUserId()));

    }

    @Test
    public void deleteUser_UserDoesntExistInRepository() {

        userService.deleteUser(testUser.getUserId());

        // verify that method userRepository.deleteById(userId) is called
        verify(userRepository).deleteById(Mockito.any());

    }

}
