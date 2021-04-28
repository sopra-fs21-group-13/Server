package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Set;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByName_success() {
        // create set list
        List<Set> emptyList = new ArrayList<>();

        // given
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

        entityManager.persist(user);
        entityManager.flush();

        // when
        User found = userRepository.findByName(user.getName());

        // then
        assertNotNull(found.getUserId());
        assertEquals(found.getName(), user.getName());
        assertEquals(found.getUsername(), user.getUsername());
        assertEquals(found.getEmail(), user.getEmail());
        assertEquals(found.getPassword(), user.getPassword());
        assertEquals(found.getLearnSets(), user.getLearnSets());
        assertEquals(found.getStatus(), user.getStatus());
        assertEquals(found.getToken(), user.getToken());
        assertEquals(found.getInGame(), user.getInGame());
        assertEquals(found.getNumberOfWins(), user.getNumberOfWins());

    }

    @Test
    public void findByUsername_success() {
        // create set list
        List<Set> emptyList = new ArrayList<>();

        // given
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

        entityManager.persist(user);
        entityManager.flush();

        // when
        User found = userRepository.findByUsername(user.getUsername());

        // then
        assertNotNull(found.getUserId());
        assertEquals(found.getName(), user.getName());
        assertEquals(found.getUsername(), user.getUsername());
        assertEquals(found.getEmail(), user.getEmail());
        assertEquals(found.getPassword(), user.getPassword());
        assertEquals(found.getLearnSets(), user.getLearnSets());
        assertEquals(found.getStatus(), user.getStatus());
        assertEquals(found.getToken(), user.getToken());
        assertEquals(found.getInGame(), user.getInGame());
        assertEquals(found.getNumberOfWins(), user.getNumberOfWins());
    }

    @Test
    public void findByEmail_success() {
        // create set list
        List<Set> emptyList = new ArrayList<>();

        // given
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

        entityManager.persist(user);
        entityManager.flush();

        // when
        User found = userRepository.findByEmail(user.getEmail());

        // then
        assertNotNull(found.getUserId());
        assertEquals(found.getName(), user.getName());
        assertEquals(found.getUsername(), user.getUsername());
        assertEquals(found.getEmail(), user.getEmail());
        assertEquals(found.getPassword(), user.getPassword());
        assertEquals(found.getLearnSets(), user.getLearnSets());
        assertEquals(found.getStatus(), user.getStatus());
        assertEquals(found.getToken(), user.getToken());
        assertEquals(found.getInGame(), user.getInGame());
        assertEquals(found.getNumberOfWins(), user.getNumberOfWins());
    }

    @Test
    public void findByStatus_success() {
        // create set list
        List<Set> emptyList = new ArrayList<>();

        // given
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

        entityManager.persist(user);
        entityManager.flush();

        // given
        User user2 = new User();
        user2.setName("Firstname2 Lastname2");
        user2.setUsername("Secondly");
        user2.setEmail("first2@email.com");
        user2.setPassword("password2");
        user2.setLearnSets(emptyList);
        user2.setStatus(UserStatus.ONLINE);
        user2.setToken("2");
        user2.setInGame(false);
        user2.setNumberOfWins(1);

        entityManager.persist(user2);
        entityManager.flush();

        // when
        List<User> foundList = userRepository.findByStatus(user2.getStatus());

        // then
        assertNotNull(foundList.get(0).getUserId());
        assertEquals(foundList.get(0).getName(), user2.getName());
        assertEquals(foundList.get(0).getUsername(), user2.getUsername());
        assertEquals(foundList.get(0).getEmail(), user2.getEmail());
        assertEquals(foundList.get(0).getPassword(), user2.getPassword());
        assertEquals(foundList.get(0).getLearnSets(), user2.getLearnSets());
        assertEquals(foundList.get(0).getStatus(), user2.getStatus());
        assertEquals(foundList.get(0).getToken(), user2.getToken());
        assertEquals(foundList.get(0).getInGame(), user2.getInGame());
        assertEquals(foundList.get(0).getNumberOfWins(), user2.getNumberOfWins());
    }


}
