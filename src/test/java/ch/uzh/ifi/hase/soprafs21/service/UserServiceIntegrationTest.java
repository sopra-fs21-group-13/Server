package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPostDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@WebAppConfiguration
@SpringBootTest
public class UserServiceIntegrationTest {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
    }

    @Test
    public void createUser_validInputs_success() {
        // given
        assertNull(userRepository.findByUsername("testUsername"));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");

        // when
        User createdUser = userService.createUser(testUser);

        // then
        assertEquals(testUser.getUserId(), createdUser.getUserId());
        assertEquals(testUser.getName(), createdUser.getName());
        assertEquals(testUser.getUsername(), createdUser.getUsername());
        assertNotNull(createdUser.getToken());
        assertEquals(UserStatus.ONLINE, createdUser.getStatus());
    }

    @Test
    public void createUser_duplicateUsername_throwsException() {
        assertNull(userRepository.findByUsername("testUsername"));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        User createdUser = userService.createUser(testUser);

        // attempt to create second user
        User testUser2 = new User();

        // change the name
        testUser2.setName("testName2");
        testUser2.setUsername("testUsername");
        testUser2.setPassword("password2");

        // check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser2));
    }

    @Test
    public void createUser_duplicateName_throwsException() {
        assertNull(userRepository.findByUsername("testUsername"));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        User createdUser = userService.createUser(testUser);

        // attempt to create second user
        User testUser2 = new User();

        // change the name
        testUser2.setName("testName");
        testUser2.setUsername("testUsername2");
        testUser2.setPassword("password2");

        // check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser2));
    }

    @Test
    public void createUser_duplicatePassword_throwsException() {
        assertNull(userRepository.findByUsername("testUsername"));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        User createdUser = userService.createUser(testUser);

        // attempt to create second user
        User testUser2 = new User();

        // change
        testUser2.setName("testName2");
        testUser2.setUsername("testUsername2");
        testUser2.setPassword("password");

        // check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser2));
    }

    @Test
    public void createUser_duplicateAll_throwsException() {
        assertNull(userRepository.findByUsername("testUsername"));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");
        User createdUser = userService.createUser(testUser);

        // attempt to create second user with same username
        User testUser2 = new User();

        // change
        testUser2.setName("testName");
        testUser2.setUsername("testUsername");
        testUser2.setPassword("password");

        // check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser2));
    }

    @Test
    public void checkForLogin_validInputs_success(){
        // given
        assertNull(userRepository.findByUsername("testUsername"));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");

        // when
        userService.createUser(testUser);
        User createdUser = userService.checkForLogin(testUser);

        // then
        assertEquals(testUser.getUserId(), createdUser.getUserId());
        assertEquals(testUser.getName(), createdUser.getName());
        assertEquals(testUser.getUsername(), createdUser.getUsername());
        assertNotNull(createdUser.getToken());
        assertEquals(UserStatus.ONLINE, createdUser.getStatus());
    }

    @Test
    public void checkForLogin_WrongUsername_throwException(){
        // given
        assertNull(userRepository.findByUsername("testUsername"));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");

        User testUser2 = new User();
        testUser2.setName("testName");
        testUser2.setUsername("testUsername2");
        testUser2.setPassword("password");

        // when
        userService.createUser(testUser);

        // check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> userService.checkForLogin(testUser2));

    }

    @Test
    public void checkForLogin_WrongPassword_throwException(){
        // given
        assertNull(userRepository.findByUsername("testUsername"));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");

        User testUser2 = new User();
        testUser2.setName("testName");
        testUser2.setUsername("testUsername");
        testUser2.setPassword("password2");

        // when
        userService.createUser(testUser);

        // check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> userService.checkForLogin(testUser2));
    }

    @Test
    public void checkUpdateUser_ValidInput_success(){
        // given
        assertNull(userRepository.findByUsername("testUsername"));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");

        // create UserPostDTO
        UserPostDTO userPostDTO = new UserPostDTO();

        userPostDTO.setName("name");
        userPostDTO.setUsername("username");
        userPostDTO.setPassword("password");
        userPostDTO.setEmail("name@email.com");
        userPostDTO.setToken("1");
        userPostDTO.setInGame(false);
        userPostDTO.setNumberOfWins(1);


        // when
        User createUser = userService.createUser(testUser);
        userPostDTO.setUserId(createUser.getUserId());
        User updateUser = userService.updateUser(userPostDTO);

        // then
        assertEquals(userPostDTO.getName(), updateUser.getName());
        assertEquals(userPostDTO.getUsername(), updateUser.getUsername());
        assertEquals(userPostDTO.getPassword(), updateUser.getPassword());
        assertEquals(userPostDTO.getEmail(), updateUser.getEmail());
        assertNotNull(updateUser.getToken());
        assertEquals(userPostDTO.isInGame(), updateUser.getInGame());
        assertEquals(userPostDTO.getNumberOfWins(), updateUser.getNumberOfWins());
    }

    @Test
    public void checkUpdateUser_WrongUserID_ThrowException(){
        // given
        assertNull(userRepository.findByUsername("testUsername"));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");

        // create UserPostDTO
        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setUserId(0L);
        userPostDTO.setName("name");
        userPostDTO.setUsername("username");
        userPostDTO.setPassword("password");
        userPostDTO.setEmail("name@email.com");
        userPostDTO.setToken("1");
        userPostDTO.setInGame(false);
        userPostDTO.setNumberOfWins(1);

        // when
        userService.createUser(testUser);

        // check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> userService.updateUser(userPostDTO));
    }

    @Test
    public void checkLogoutUser_ValidInputs_success(){
        // given
        assertNull(userRepository.findByUsername("testUsername"));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");

        // when
        // when
        User createdUser = userService.createUser(testUser);
        User loggedOutUser = userService.logoutUser(createdUser.getUserId());

        assertEquals(UserStatus.OFFLINE, loggedOutUser.getStatus());

    }

    @Test
    public void checkLogoutUser_InvalidUserId_ThrowException(){
        // given
        assertNull(userRepository.findByUsername("testUsername"));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");

        // when
        userService.createUser(testUser);

        // check that an error is thrown
        assertThrows(ResponseStatusException.class, () -> userService.logoutUser(0L));

    }

    @Test
    public void checkDeleteUser_ValidInput_success(){
        // given
        assertNull(userRepository.findByUsername("testUsername"));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("password");

        // when
        User createdUser = userService.createUser(testUser);
        userService.deleteUser(createdUser.getUserId());

        // check
        assertNull(userRepository.findByUsername(testUser.getUsername()));
        assertNull(userRepository.findByName(testUser.getName()));
        assertNull(userRepository.findByPassword(testUser.getPassword()));

    }


}
