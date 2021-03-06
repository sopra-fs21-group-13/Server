package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPostDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back to the caller.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private static final AtomicInteger count = new AtomicInteger(0);

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get all users
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    // Get all online users
    public List<User> getOnlineUsers() {
        return this.userRepository.findByStatus(UserStatus.ONLINE);
    }

    // Get user by id
    public User getUser(long id) {
        Optional<User> checkUser = userRepository.findById(id);

        if (checkUser.isPresent()){
            return checkUser.get();
        }
        else{
            String message = "The user with id: %s can't be found in the database.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(message, id));
        }
    }

    // Create a user entity
    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.ONLINE);

        checkIfUserExists(newUser);

        // saves the given entity but data is only persisted in the database once flush() is called
        newUser = userRepository.save(newUser);
        userRepository.flush();

        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    /**
     * This is a helper method that will check the uniqueness criteria of the username and the name
     * defined in the User entity. The method will do nothing if the input is unique and throw an error otherwise.
     *
     * @param userToBeCreated
     * @throws org.springframework.web.server.ResponseStatusException
     * @see User
     */

    // check for validity of input
    private void checkIfUserExists(User userToBeCreated) {
        User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());
        User userByName = userRepository.findByName(userToBeCreated.getName());
        User userByPassword = userRepository.findByPassword(userToBeCreated.getPassword());

        String baseErrorMessage = "The %s provided %s not unique. Therefore, the user could not be created!";
        if (userByUsername != null && userByName != null && userByPassword != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage, "username, name, and password", "are"));
        }
        else if (userByUsername != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage, "username", "is"));
        }
        else if (userByName != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage, "name", "is"));
        }
        else if (userByPassword != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage, "password", "is"));
        }
    }

    // check if username and password correspond
    public User checkForLogin(User userToLogin) {
        User userByUsername = userRepository.findByUsername(userToLogin.getUsername());
        String baseErrorMessage = "The user doesn't exist. Please check the credentials and password!";
        if (userByUsername != null) {
            if (userByUsername.getPassword().equals(userToLogin.getPassword())) {
                userToLogin.setName(userByUsername.getName());
                userToLogin.setToken(userByUsername.getToken());
                userToLogin.setStatus(UserStatus.ONLINE);
                userToLogin.setUserId(userByUsername.getUserId());
                //update repo as "ONLINE"
                userByUsername.setStatus(UserStatus.ONLINE);
                userRepository.save(userByUsername);
                userRepository.flush();
                return userToLogin;
            }
            else {
                baseErrorMessage = "Incorrect Password Or UserName";
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage));
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage));
        }
    }

    //need to create a PW
    public User upserd (User user){

        if(userRepository.findByEmail(user.getEmail())==null)
        {
            int uniqueUsername = count.incrementAndGet();
            user.setUsername("NoName" + uniqueUsername);
            user.setPassword("defaultPassword");
            user.setStatus(UserStatus.ONLINE);

            User newUser = userRepository.save(user);
            return newUser;
        }
        else{
            User newUser = userRepository.findByEmail(user.getEmail());
            return newUser;
        }
    }

    // update user data
    public User updateUser(UserPostDTO userPostDTO) {

        User user = userRepository.findById(userPostDTO.getUserId()).orElseThrow(IllegalArgumentException::new);

        if (userPostDTO.getUsername() != null) {
                user.setUsername(userPostDTO.getUsername());}
        if (userPostDTO.getName() != null) {
                user.setName(userPostDTO.getName()); }
        if (userPostDTO.getPassword() != null) {
                user.setPassword(userPostDTO.getPassword()); }
        if (userPostDTO.getEmail() != null) {
                user.setEmail(userPostDTO.getEmail()); }
        if (userPostDTO.getPhoto() != null) {
            user.setPhoto(userPostDTO.getPhoto()); }

        user.setInGame(userPostDTO.isInGame());
        user.setNumberOfWins(userPostDTO.getNumberOfWins());

        user = userRepository.save(user);
        userRepository.flush();
        return user;
    }

    // log out a user
    public User logoutUser(Long userId){

        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);

        user.setStatus(UserStatus.OFFLINE);
        user = userRepository.save(user);
        userRepository.flush();
        return user;
    }

    // Delete a User
    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

}
